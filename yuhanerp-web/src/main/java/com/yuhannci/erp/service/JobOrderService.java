package com.yuhannci.erp.service;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collections;

import org.apache.poi.util.StringUtil;


//import org.assertj.core.util.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yuhannci.erp.mapper.JobOrderMapper;
import com.yuhannci.erp.mapper.ConceptJobOrderMapper;
import com.yuhannci.erp.mapper.EquipmentOrderPartMapper;
import com.yuhannci.erp.mapper.CustomerMapper;
import com.yuhannci.erp.model.JobStageEnum;
import com.yuhannci.erp.model.JobTypeEnum;
import com.yuhannci.erp.model.KeyValueEntry;
import com.yuhannci.erp.model.OrderTypeEnum;
import com.yuhannci.erp.model.SourceCategoryEnum;
import com.yuhannci.erp.model.db.ConceptJobOrder;
import com.yuhannci.erp.model.db.JobOrder;
import com.yuhannci.erp.model.JobOrderProgress;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ineti
 *
 */
@Service
@Slf4j
public class JobOrderService {
	
	@Autowired JobOrderMapper jobOrderMapper;
	@Autowired ConceptJobOrderMapper conceptJobOrderMapper;
	
	@Autowired EquipmentOrderPartMapper equipmentOrderPartMapper;
	@Autowired CustomerMapper partnerMapper;
	
	@Autowired OrderNoService orderNoService;

	public boolean checkOrderNoExists(JobTypeEnum jobType, OrderTypeEnum orderType, String orderNo){
		SimpleEntry<String, String> src = orderNoService.parseOrderNo(orderNo);
		return checkOrderNoExists(jobType, orderType, src);
	}
	
	public boolean checkOrderNoExists(JobTypeEnum jobType, OrderTypeEnum orderType, SimpleEntry<String, String> src){
		return checkOrderNoExists(jobType, orderType, src.getKey(), src.getValue());
	}
	public boolean checkOrderNoExists(JobTypeEnum jobType, OrderTypeEnum orderType, String orderNoBase, String orderNoExtra){
		return jobType == JobTypeEnum.JOB ?
					jobOrderMapper.selectJobOrderExists(orderType.toString(), orderNoBase, orderNoExtra) != null
					: conceptJobOrderMapper.findConcept(null, jobType.toString(), orderNoBase, orderNoExtra) != null;
	}
	public List<KeyValueEntry> getIngOrderList(){
		return jobOrderMapper.selectIngOrderList();
	}
	public Long getIngOrderListFirst(){
		return jobOrderMapper.selectIngOrderListFirst();
	}
	
	// ?????? ======================================================================================================================================================================================
	
	@Transactional
	public void addNewConcept(ConceptJobOrder data){
		conceptJobOrderMapper.insertConceptJob(data);
	}	
	


	// ?????? ????????? ?????? ??????
	public List<ConceptJobOrder> getFinishedConcept(OrderTypeEnum type, boolean activeList, String keyword, String orderNoBase, String orderNoExtra, Date orderDateFrom, Date orderDateTo){
		
		return conceptJobOrderMapper.selectConcept(type.toString(), orderNoBase, orderNoExtra, true, activeList, false, keyword, orderDateFrom, orderDateTo);
	}
	// ?????? ???????????? ?????? ??????
	public List<ConceptJobOrder> getUnfinishedConcept(OrderTypeEnum type, boolean activeList, String keyword, String orderNoBase, String orderNoExtra, Date orderDateFrom, Date orderDateTo){
		
		return conceptJobOrderMapper.selectConcept(type.toString(), orderNoBase, orderNoExtra, false, activeList, false, keyword, orderDateFrom, orderDateTo);
	}
	
	public ConceptJobOrder getSpecificConcept(Long id){
		if(id == null)
			throw new IllegalArgumentException("id ?????? ????????? ????????? ??? ????????????");
		
		return getSpecificConcept(id, null, null, null);
	}
	
	public ConceptJobOrder getSpecificConcept(Long id, OrderTypeEnum orderType, String orderNo){
		SimpleEntry<String, String> src = orderNoService.parseOrderNo(orderNo);
		log.info("orderNo[" + orderNo + "] ==> [" + src.getKey() + " - " + src.getValue() + "]");
		return getSpecificConcept(id, orderType, src.getKey(), src.getValue());
	}
	
	public ConceptJobOrder getSpecificConcept(Long id, OrderTypeEnum orderType, String orderNoBase, String orderNoExtra){
		if(id == null && orderType == null)		// order-no ??? ????????? order-Type ??? ??????. 
			throw new IllegalArgumentException("?????? ??????(Order-Type]??? ???????????? ??????????????? ");
				
		return conceptJobOrderMapper.findConcept(id
													, orderType != null ? orderType.toString() : null
													, orderNoBase
													, orderNoExtra);		
	}
	
	@Transactional
	public void modifyConcept(ConceptJobOrder data) throws Exception{
		if(data.getId() == null || data.getId() == 0)
			throw new InvalidParameterException("?????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(data.getOrderType()))
			throw new InvalidParameterException("?????? ?????? ????????? ????????????");
		if(StringUtils.isEmpty(data.getLastModifiedUserId()))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
		
		// ?????? ?????? ??????
		ConceptJobOrder org = conceptJobOrderMapper.findConcept(data.getId(), data.getOrderType(), null, null);
		HashMap<String, SimpleEntry<String, String>> altered = data.compare(org);
		log.debug("????????? ?????? ?????? = " + altered);
		
		// ????????? ?????? ??????
		SimpleEntry<String, String> val;
		for(String key : altered.keySet()){
			val = altered.get(key);			
			// jobOrderChangeHistoryService.addFieldChange(JobStageEnum.CONCEPT, data.getId(), key, val.getKey(), val.getValue(), data.getLastModifiedUserId());
		}
		
		conceptJobOrderMapper.updateConceptJob(data);
	}
		
	public void deleteConcept(Long id, String lastModifiedUserId){
		if(id == null || id == 0)
			throw new InvalidParameterException("?????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(lastModifiedUserId))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
	
//		jobOrderChangeHistoryService.addDeleteStatus(JobStageEnum.CONCEPT, id, lastModifiedUserId);
		conceptJobOrderMapper.markConceptJobDeleted(id, lastModifiedUserId);
	}
	
	public void makeConceptFinished(Long id, String lastModifiedUserId){
		if(id == null || id == 0)
			throw new InvalidParameterException("?????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(lastModifiedUserId))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
		
//		jobOrderChangeHistoryService.addFinishStatus(JobStageEnum.CONCEPT, id, lastModifiedUserId);		
		conceptJobOrderMapper.markConceptJobFinished(id, lastModifiedUserId, null);
	}	
	
	// ??????/????????? ======================================================================================================================================================================================
	public JobOrder searchJob(String orderNo, boolean includeParts){
		
		SimpleEntry<String, String> parsedOrderNo = orderNoService.parseOrderNo(orderNo);
		
		JobOrder equip = jobOrderMapper.selectSingleOrder(parsedOrderNo.getKey(), parsedOrderNo.getValue(), null);
		return equip;
	}
	
	public JobOrder getSpecificJob(Long id){
		
		JobOrder equip = jobOrderMapper.selectSingleOrder(null, null, id);
		return equip;
	}
	
	public JobOrder searchJob(Long id){
		
		JobOrder equip = jobOrderMapper.selectSingleOrder(null, null, id);
		return equip;
	}
	
	List<JobOrder> getUnfinished(OrderTypeEnum type, String keyword
								, String orderNoBase, String orderNoExtra
								, Date orderDateFrom, Date orderDateTo
								, Date deliveryDateFrom, Date deliveryDateTo
								, Date designDateFrom, Date designDateTo){
		List<JobOrder> equips = jobOrderMapper.selectJobOrder(type != null ? type.toString() : null, 
																orderNoBase, orderNoExtra, 
																"N", 
																keyword, 
																orderDateFrom, orderDateTo, 
																deliveryDateFrom, deliveryDateTo
																, designDateFrom, designDateTo);
		
		System.out.println("&&&&&&&&&&&&"+orderNoBase);
		return equips;
	}

	
	public List<JobOrder> getUnfinishedJob(OrderTypeEnum type, String keyword, String orderNoBase, String orderNoExtra, Date orderDateFrom, Date orderDateTo, Date deliveryDateFrom, Date deliveryDateTo, Date designDateFrom, Date designDateTo){
		return getUnfinished(type, keyword, orderNoBase, orderNoExtra, orderDateFrom, orderDateTo, deliveryDateFrom, deliveryDateTo, designDateFrom, designDateTo);
	}		

	public List<JobOrder> getFinishedJob(OrderTypeEnum type, String keyword, String orderNoBase, String orderNoExtra, Date orderDateFrom, Date orderDateTo, Date deliveryDateFrom, Date deliveryDateTo){
		List<JobOrder> equips = jobOrderMapper.selectJobOrder(type.toString(), 
				orderNoBase, orderNoExtra, 
				"Y", 
				keyword, 
				orderDateFrom, orderDateTo, 
				deliveryDateFrom, deliveryDateTo, null, null);
		
		return equips;
	}
	
	public List<JobOrderProgress> getJobOrderProgress(String orderType, String orderNoBase, String orderNoExtra){
		return jobOrderMapper.selectJobOrderProgress(orderType, orderNoBase, orderNoExtra);
	}
	public JobOrderProgress getJobOrderProgressSingle(Long key){
		return jobOrderMapper.selectJobOrderProgressSingle(key);
	}
	public List<JobOrderProgress> getJobOrderProgressPage(String orderType, Integer psize, Integer page){
		return jobOrderMapper.selectJobOrderProgressPage(orderType, psize, page);
	}
	
	@Transactional
	public void modifyJob(JobOrder data, String who) throws Exception{
		if(data.getId() == null || data.getId() == 0)
			throw new InvalidParameterException("??????/????????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(who))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
		
		// ?????? ??? 
		JobOrder org = jobOrderMapper.selectSingleOrder(null, null, data.getId());
		
		// ?????? ?????? ??????
		HashMap<String, SimpleEntry<String, String>> altered = data.compare(org.extractFields(), null);
		log.debug("????????? ?????? ?????? = " + altered);
		
		data.setLastModifiedUserId(who);
		
		jobOrderMapper.updateJob(data);
		
		// jobOrderChangeHistoryService.addJobOrderChangeHistory(who, data.getId(), JobStageEnum.JOB, org.getOrderType(), SourceCategoryEnum.JOB, altered);
	}	
	
	@Transactional
	public Long addJob(JobOrder order){
	
		jobOrderMapper.insertJob(order);
		Long jobOrderId = order.getId();
		log.debug("????????? ?????? ?????? ID = "  + jobOrderId);
		return jobOrderId;
	}
	
	
	public void reUpdateJob(JobOrder order) {
		
		jobOrderMapper.reUpdateJob(order);
		
		
	}
	
	public String selectJobOrderFromPush(Long id) {
		return jobOrderMapper.selectJobOrderFromPush(id);
	}
	
	@Transactional
	public Long addJobFromConcept(JobOrder order, Long conceptId, String userId){
		Long jobOrderId = addJob(order);
		log.debug("??????[" + conceptId + "] ?????? ????????? ?????? ?????? ID = "  + jobOrderId);
		conceptJobOrderMapper.markConceptJobIsOrdered(conceptId, jobOrderId, userId);
		return jobOrderId;
	}
	
	public void deleteJob(Long id, String lastModifiedUserId){
		if(id == null || id == 0)
			throw new InvalidParameterException("?????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(lastModifiedUserId))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
		
		// jobOrderChangeHistoryService.addDeleteStatus(JobStageEnum.JOB, id, lastModifiedUserId);
		jobOrderMapper.deleteJob(id, null, null, lastModifiedUserId);
	}
	
	@Transactional
	public void makeJobFinished(Long id, String lastModifiedUserId){
		if(id == null || id == 0)
			throw new InvalidParameterException("????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(lastModifiedUserId))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
				
		//jobOrderChangeHistoryService.addFinishStatus(JobStageEnum.JOB, id, lastModifiedUserId);		
		jobOrderMapper.markFinishedJob(id, null, null, lastModifiedUserId);
	}	
	@Transactional
	public void makeJobDelivered(Long id, String lastModifiedUserId){
		if(id == null || id == 0)
			throw new InvalidParameterException("?????? ????????? ????????? ????????? ID ??? ???????????????");
		if(StringUtils.isEmpty(lastModifiedUserId))
			throw new InvalidParameterException("????????? ?????????ID ??? ????????????");
				
		// jobOrderChangeHistoryService.addStatusChange(JobStageEnum.JOB, id, lastModifiedUserId, StatusEnum.Delivered);		
		jobOrderMapper.markJobDelivered(id, lastModifiedUserId);
	}	

	final Set<String> estimationFilters = new HashSet<String>(Arrays.asList("installDate", "realInstallDate", "shippingDate", "businessUserId", "note", "quantity", "internalUnitPrice", "estimatedPrice", "negotiatedPrice"));

	// ?????? ?????? ?????? - ?????? ?????? ??????
	@Transactional
	public void updateJobEstimation(JobOrder order, String lastModifiedUserId) throws Exception {

		try{
			JobOrder org = jobOrderMapper.selectSingleOrder(null, null, order.getId());
			
			// ?????? ?????? ??????
			HashMap<String, SimpleEntry<String, String>> diff = order.compare(org.extractFields(estimationFilters), estimationFilters);
			log.debug("????????? ?????? ?????? = " + diff);
			
			order.setLastModifiedUserId(lastModifiedUserId);
			jobOrderMapper.updateEstimation(order);
			
			//jobOrderChangeHistoryService.addJobOrderChangeHistory(lastModifiedUserId, order.getId(), JobStageEnum.JOB, org.getOrderType(), SourceCategoryEnum.JOB, diff);
		}catch(Exception e){
			log.error("?????? ?????? ?????? ??? ??????", e);
			throw e;
		}
	}
	
	@Transactional
	public void updateJobOrderDesignProgress(Long jobOrderId, int progress, String userId) throws Exception{
		JobOrder data = new JobOrder();
		data.setId(jobOrderId);
		data.setDesignProgress(progress);
		data.setLastModifiedUserId(userId);
		data.setLastModifiedWhen(new Date());
		
		if(progress > 100)
			throw new Exception("?????? ?????? ???????????? 100% ??? ?????? ??? ????????????");
		
		if(progress == 100){
			// ?????? ?????? ??????
		}else{
			// ?????? ?????? ?????????
		}
		
		jobOrderMapper.updateDesignProgress(data);
	}
}
