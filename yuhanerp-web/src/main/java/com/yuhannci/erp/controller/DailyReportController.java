package com.yuhannci.erp.controller;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yuhannci.erp.model.JobConceptRegForm;
import com.yuhannci.erp.model.JobConceptRegForm.RegMode;
import com.yuhannci.erp.model.EquipmentDeliveryForm;
import com.yuhannci.erp.model.JobOrderForm;
import com.yuhannci.erp.model.JobTypeEnum;
import com.yuhannci.erp.model.OrderTypeEnum;
import com.yuhannci.erp.model.StandardResponse;
import com.yuhannci.erp.model.db.ConceptJobOrder;
import com.yuhannci.erp.model.db.JobDelivery;
import com.yuhannci.erp.model.db.JobOrder;
import com.yuhannci.erp.service.JobDeliveryService;
import com.yuhannci.erp.service.JobOrderChangeHistoryService;
import com.yuhannci.erp.service.JobOrderService;
import com.yuhannci.erp.service.LoginDongleService;
import com.yuhannci.erp.service.MessageService;
import com.yuhannci.erp.service.NoticeMessageService;
import com.yuhannci.erp.service.OrderNoService;
import com.yuhannci.erp.service.PushAlarmService;
import com.yuhannci.erp.service.RegisteredFileService;
import com.yuhannci.erp.service.UserService;
import com.yuhannci.erp.service.RegisteredFileService.FileCategory;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DailyReportController {

	@Autowired MessageService messageService;
	@Autowired LoginDongleService loginDongleService;
	@Autowired RegisteredFileService registeredFileService;
	@Autowired JobOrderService jobOrderService;
	@Autowired JobDeliveryService jobDeliveryService;
	@Autowired UserService userService;
	@Autowired OrderNoService orderNoService;
	@Autowired JobOrderChangeHistoryService jobOrderChangeHistoryService;
	@Autowired NoticeMessageService noticeMessageService;
	@Autowired PushAlarmService pushAlarmService;
	
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public enum ConceptRequestedAction{
		edit,
		delete,
		finish,
		A,
		B,
		C,
		D,
		E,
		F
	}
	
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	// ???????????? - ?????????
	@RequestMapping("/daily_report_list")
	public ModelAndView list(String orderNoBase, String orderNoExtra, String keyword){
		
		String adjustedKeyword = !StringUtils.isEmpty(keyword) ? keyword.trim() : null;
		String adjustedorderNoBase = !StringUtils.isEmpty(orderNoBase) ? orderNoBase.trim() : null;
		String adjustedorderNoExtra = !StringUtils.isEmpty(orderNoExtra) ? orderNoExtra.trim() : null;
		log.info("keyword = [" + adjustedKeyword + "], orderNoBase = [" + adjustedorderNoBase + "], orderNoExtra = " + adjustedorderNoExtra);
		
		ModelAndView mv = new ModelAndView("daily_report/list");
		// mv.addObject("isBusinessUser", userService.isBusinessDeptUser( loginDongleService.getLoginId() ) );
		mv.addObject("orderNoBase", adjustedorderNoBase);
		mv.addObject("orderNoExtra", adjustedorderNoExtra);
		mv.addObject("keyword", adjustedKeyword);
		return mv;
	}
	// ???????????? - ?????????
	@RequestMapping("/daily_report_print")
	public ModelAndView print(String orderNoBase, String orderNoExtra, String keyword){
		
		String adjustedKeyword = !StringUtils.isEmpty(keyword) ? keyword.trim() : null;
		String adjustedorderNoBase = !StringUtils.isEmpty(orderNoBase) ? orderNoBase.trim() : null;
		String adjustedorderNoExtra = !StringUtils.isEmpty(orderNoExtra) ? orderNoExtra.trim() : null;
		log.info("keyword = [" + adjustedKeyword + "], orderNoBase = [" + adjustedorderNoBase + "], orderNoExtra = " + adjustedorderNoExtra);
		
		ModelAndView mv = new ModelAndView("daily_report/print");
		mv.addObject("isBusinessUser", userService.isBusinessDeptUser( loginDongleService.getLoginId() ) );
		mv.addObject("orderNoBase", adjustedorderNoBase);
		mv.addObject("orderNoExtra", adjustedorderNoExtra);
		mv.addObject("keyword", adjustedKeyword);
		return mv;
	}
	
	// ???????????? - ??????&??????
	@RequestMapping("/daily_report_write")
	public ModelAndView write(){
		ModelAndView mv = new ModelAndView("daily_report/write");
		mv.addObject("isBusinessUser", userService.isBusinessDeptUser( loginDongleService.getLoginId() ) );
		return mv;				
	}
	
	// ???????????? - ?????? ????????????
	@RequestMapping("/daily_report_archive")
	public ModelAndView archive(String keyword, String orderNoBase, String orderNoExtra, String orderDateFrom, String orderDateTo, String deliveryDateFrom, String deliveryDateTo){
		String adjustedKeyword = !StringUtils.isEmpty(keyword) ? keyword.trim() : null;
		String adjustedorderNoBase = !StringUtils.isEmpty(orderNoBase) ? orderNoBase.trim() : null;
		String adjustedorderNoExtra = !StringUtils.isEmpty(orderNoExtra) ? orderNoExtra.trim() : null;
		
		String adjustedOrderDateFrom = !StringUtils.isEmpty(orderDateFrom) ? orderDateFrom.trim() : null;
		String adjustedOrderDateTo = !StringUtils.isEmpty(orderDateTo) ? orderDateTo.trim() : null;
		String adjustedDeliveryDateFrom = !StringUtils.isEmpty(deliveryDateFrom) ? deliveryDateFrom.trim() : null;
		String adjustedDeliveryDateTo = !StringUtils.isEmpty(deliveryDateTo) ? deliveryDateTo.trim() : null;
		
		log.info("keyword = [" + adjustedKeyword + "], orderNoBase = [" + adjustedorderNoBase + "], orderNoExtra = " + adjustedorderNoExtra);
		
		
		ModelAndView mv = new ModelAndView("daily_report/archive");
		mv.addObject("isBusinessUser", userService.isBusinessDeptUser( loginDongleService.getLoginId() ) );
		mv.addObject("orderNoBase", adjustedorderNoBase);
		mv.addObject("orderNoExtra", adjustedorderNoExtra);
		mv.addObject("keyword", adjustedKeyword);
		
		mv.addObject("orderDateFrom", adjustedOrderDateFrom);
		mv.addObject("orderDateTo", adjustedOrderDateTo);
		mv.addObject("deliveryDateFrom", adjustedDeliveryDateFrom);
		mv.addObject("deliveryDateTo", adjustedDeliveryDateTo);
		return mv;				
	}
	
	// ???????????? - ?????? ????????? ??????
	@RequestMapping("/daily_report_left_message")
	public ModelAndView leftMessage(){
				
		ModelAndView mv = new ModelAndView("daily_report/left_message");
		mv.addObject("recentMessage", messageService.getRecentMessage(loginDongleService.getLoginId()));
		return mv;		
	}
	
	final static SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
	
	// ??????/????????? ?????? ?????? ?????? ??????
	// no-parameter --> ??????
	
	@RequestMapping("/daily_report/popup/new_{orderType}")
	public ModelAndView popupEquipmentProcessJob(@PathVariable("orderType") String orderType, Long fromConcept, Long fromJob){
		
		ModelAndView mv = new ModelAndView("daily_report/write_sub/popup_new_jobjig");
		
		final boolean isJob = orderType.equalsIgnoreCase("JOB");
		
		JobOrder data = null;
		if(fromConcept != null){			
			ConceptJobOrder concept = jobOrderService.getSpecificConcept(fromConcept, isJob ? OrderTypeEnum.JOB : OrderTypeEnum.JIG, null);
			data = new JobOrder();
			data.setOrderType(isJob ? "JOB" : "JIG");
			data.setOrderNoBase(concept.getOrderNoBase());
			data.setOrderNoExtra(concept.getOrderNoExtra());
			data.setCustomerId(concept.getCustomerId());			
			data.setDevice(concept.getDevice());
			data.setBusinessUserId(concept.getBusinessUserId());
			data.setDesignUserId(concept.getDesignUserId());
			data.setCustomerUser(concept.getCustomerUser());
			data.setNote(concept.getNote());
			data.setQuantity(concept.getQuantity());
			data.setInternalUnitPrice(concept.getInternalUnitPrice());
			data.setNegotiatedPrice(concept.getNegotiatedPrice());
			data.setEstimatedPrice( concept.getEstimatedPrice());
			
			log.debug("?????? ????????? = " + concept);
			log.debug("?????? ????????? = "+ data);
		}else if(fromJob != null){
			data = jobOrderService.searchJob(fromJob);
		}
		if(data== null)
			data = new JobOrder();

		mv.addObject("jobType", isJob ? "JOB" : "JIG");
		mv.addObject("jobTypeDesc" , isJob ? "??????" : "?????????");
		mv.addObject("fromConcept", fromConcept);
		mv.addObject("data", data);
		mv.addObject("today", ymdSDF.format(new Date()));
		return mv;				
	}
	
	//?????? ????????? ??????
	
	@RequestMapping("/daily_report/popupNew/new_{orderType}")
	public ModelAndView popupEquipmentProcessJobNew(@PathVariable("orderType") String orderType, Long fromConcept, Long fromJob){
		
		ModelAndView mv = new ModelAndView("daily_report/write_sub/popup_new_jobjigRe");
		
		final boolean isJob = orderType.equalsIgnoreCase("JOB");
		
		JobOrder data = null;
		if(fromConcept != null){			
			ConceptJobOrder concept = jobOrderService.getSpecificConcept(fromConcept, isJob ? OrderTypeEnum.JOB : OrderTypeEnum.JIG, null);
			data = new JobOrder();
			data.setOrderType(isJob ? "JOB" : "JIG");
			data.setOrderNoBase(concept.getOrderNoBase());
			data.setOrderNoExtra(concept.getOrderNoExtra());
			data.setCustomerId(concept.getCustomerId());			
			data.setDevice(concept.getDevice());
			data.setBusinessUserId(concept.getBusinessUserId());
			data.setDesignUserId(concept.getDesignUserId());
			data.setCustomerUser(concept.getCustomerUser());
			data.setNote(concept.getNote());
			data.setQuantity(concept.getQuantity());
			data.setInternalUnitPrice(concept.getInternalUnitPrice());
			data.setNegotiatedPrice(concept.getNegotiatedPrice());
			data.setEstimatedPrice( concept.getEstimatedPrice());
			
			log.debug("?????? ????????? = " + concept);
			log.debug("?????? ????????? = "+ data);
		}else if(fromJob != null){
			data = jobOrderService.searchJob(fromJob);
		}
		if(data== null)
			data = new JobOrder();

		mv.addObject("jobType", isJob ? "JOB" : "JIG");
		mv.addObject("jobTypeDesc" , isJob ? "??????" : "?????????");
		mv.addObject("fromConcept", fromConcept);
		mv.addObject("fromJob", fromJob);
		mv.addObject("data", data);
		mv.addObject("today", ymdSDF.format(new Date()));
		return mv;				
	}
	
	@RequestMapping("/daily_report/register_job")
	public @ResponseBody StandardResponse registerJob(@Valid JobOrder form, String fromConcept, BindingResult result){
		
		StandardResponse response = new StandardResponse();
		try{
			Long jobOrderId = 0L;
			
			if(StringUtils.isEmpty(form.getOrderNoBase()) )
				throw new IllegalArgumentException("Order-No ????????? ????????? ????????? ????????? ?????????");
			
			if(StringUtils.isEmpty(form.getDevice()))
				throw new IllegalArgumentException("Device ????????? ????????? ?????????");
			
			if(jobOrderService.checkOrderNoExists(JobTypeEnum.JOB, OrderTypeEnum.parse(form.getOrderType()), form.getOrderNoBase(), form.getOrderNoExtra()))
				throw new IllegalArgumentException("?????? ???????????? Order-No ?????????");
			
			if(StringUtils.isEmpty(form.getCustomerId()))
				throw new IllegalArgumentException("????????? ????????? ?????????");
			
			if( !StringUtils.isEmpty(fromConcept) ){
				Long conceptId = Long.parseLong(fromConcept);
				log.info("?????? Concept-ID = " + conceptId);
				
				jobOrderId = jobOrderService.addJobFromConcept(form, conceptId, loginDongleService.getLoginId());
			}
			else
				jobOrderId = jobOrderService.addJob(form);
							
			response = StandardResponse.createResponse("OK");
			response.setData(jobOrderId);
			
			noticeMessageService.addRegisteredNotice(jobOrderId, "G", "????????????", "?????? ???????????? ??????", form.getDevice(), noticeMessageService.getCustomerNameToNotice(jobOrderId));
			pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(jobOrderId)+"]", "Order Start");
			
			
			
		}catch(IllegalArgumentException e){
			log.error("?????? ?????? ?????? ??? ??????. ??? ????????? = " + form, e);			
			response.setResult("ERROR");
			response.setReason(e.getMessage());
			
		}catch(Exception e){
			log.error("?????? ?????? ?????? ??? ??????. ??? ????????? = " + form, e);			
			response.setResult("ERROR");
			response.setReason("?????? ?????? ??? ????????? ?????????????????????");
		}
		
		return response;
	}
	
	//????????? ??????
	@RequestMapping("/daily_report/register_jobNew")
	public @ResponseBody StandardResponse registerJobNew(@Valid JobOrder form, String fromConcept, BindingResult result, Long fromJob){
		
	
		
		StandardResponse response = new StandardResponse();
		try{
			
			form.setId(fromJob);
			
			if(StringUtils.isEmpty(form.getOrderNoBase()) )
				throw new IllegalArgumentException("Order-No ????????? ????????? ????????? ????????? ?????????");
			
			if(StringUtils.isEmpty(form.getDevice()))
				throw new IllegalArgumentException("Device ????????? ????????? ?????????");
			
			if(StringUtils.isEmpty(form.getCustomerId()))
				throw new IllegalArgumentException("????????? ????????? ?????????");
			
			System.out.println("???????????? user = "+form.getBusinessUserId());
			System.out.println("?????????  ID = "+form.getCustomerId());
			System.out.println("OrderNo = "+form.getOrderNoBase());
			System.out.println("????????? ID = "+form.getDesignUserId());
		
			jobOrderService.reUpdateJob(form);
							
			response = StandardResponse.createResponse("OK");
			response.setData(fromJob);
			
			
			
			noticeMessageService.addRegisteredNotice(fromJob, "G", "?????????", "????????? ???????????? ??????", form.getDevice(), noticeMessageService.getCustomerNameToNotice(fromJob));
			
			pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(form.getId())+"]", "Order ReStart");
			
		}catch(IllegalArgumentException e){
			log.error("?????? ?????? ?????? ??? ??????. ??? ????????? = " + form, e);			
			response.setResult("ERROR");
			response.setReason(e.getMessage());
			
		}catch(Exception e){
			log.error("?????? ?????? ?????? ??? ??????. ??? ????????? = " + form, e);			
			response.setResult("ERROR");
			response.setReason("?????? ?????? ??? ????????? ?????????????????????");
		}
		
		return response;
		
	}
	
	// ?????? ?????? ?????? ??????
	@RequestMapping("/daily_report/popup_job_delivery")
	public ModelAndView popupEquipmentDeliverReg(Long jobOrderId){
		
		JobOrder data = null;
		try{
			
			data = jobOrderService.searchJob(jobOrderId);
			if(data ==null)
				throw new Exception("no such job[" + jobOrderId + "]");
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		ModelAndView mv = new ModelAndView("daily_report/write_sub/popup_job_delivery");		
		mv.addObject("data", data);
		return mv;						
	}
	
	@RequestMapping("/daily_report/register_delivery")
	public @ResponseBody StandardResponse registerDelivery(@Valid EquipmentDeliveryForm form
												, BindingResult result
												, MultipartFile image_file1
												, MultipartFile image_file2
												, MultipartFile image_file3
												, MultipartFile image_file4
												, MultipartFile image_file5){
		StandardResponse response = new StandardResponse();
		try{
			if(result.hasErrors())
				throw new Exception("????????? ????????? ?????????" + result.toString());
			
			SimpleEntry<String, String> orderNo = orderNoService.getOrderNoFromJobOrderId(form.getJobOrderId());
			
			JobDelivery data = new JobDelivery();
			data.setJobOrderId(form.getJobOrderId());
			data.setOrderNoBase(orderNo.getKey());
			data.setOrderNoExtra(orderNo.getValue());
			data.setCarrierName(form.getCarrierName());
			data.setCarrierType(form.getCarrierType());
			data.setDestination(form.getDestination());
			data.setShippingFee(form.getShippingFee());

			if(image_file1 != null)
				data.setImageFileNo1( registeredFileService.saveFile(image_file1,FileCategory.IMG, "DELIVERY", loginDongleService.getLoginId()) );
			if(image_file2 != null)
				data.setImageFileNo2( registeredFileService.saveFile(image_file2,FileCategory.IMG, "DELIVERY", loginDongleService.getLoginId()) );
			if(image_file3 != null)
				data.setImageFileNo3( registeredFileService.saveFile(image_file3,FileCategory.IMG, "DELIVERY", loginDongleService.getLoginId()) );
			if(image_file4 != null)
				data.setImageFileNo4( registeredFileService.saveFile(image_file4,FileCategory.IMG, "DELIVERY", loginDongleService.getLoginId()) );
			if(image_file5 != null)
				data.setImageFileNo5( registeredFileService.saveFile(image_file5,FileCategory.IMG, "DELIVERY", loginDongleService.getLoginId()) );
			
			jobDeliveryService.registerDelivery(data, loginDongleService.getLoginId());
			
			noticeMessageService.addRegisteredNotice(form.getJobOrderId(), "G", "????????????", "????????? ?????????????????????.", noticeMessageService.getDeviceToNotice(form.getJobOrderId()), noticeMessageService.getCustomerNameToNotice(form.getJobOrderId()));
			pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(form.getJobOrderId())+"]", "Order Shipment");
			response.setResult("OK");
			
		}catch(Exception e){
			response.setResult("ERROR");
			response.setReason(e.getMessage());
		}
		return response;
	}
	
	// ?????? ?????? ??????/?????? ??????
	@RequestMapping("/daily_report/popup/{orderType}_concept")
	public ModelAndView popupEquipmentConceptJob(@PathVariable("orderType") String orderType, Long id) throws Exception {
		ModelAndView mv = new ModelAndView("daily_report/write_sub/popup_new_job_concept");
		
		ConceptJobOrder conceptData = new ConceptJobOrder();	// for dummy
		
		if(id != null){
			conceptData = jobOrderService.getSpecificConcept(id, OrderTypeEnum.parse(orderType), null);
			if(conceptData == null){
				log.error("????????? id[" + id + "] ??? ?????? ????????? ?????? ??? ??????");
				return null;
			}
		}
		
		mv.addObject("orderType", orderType);
		mv.addObject("orderTypeDesc", orderType.equalsIgnoreCase("JOB") ? "??????" : "?????????" );
		mv.addObject("id", id);
		mv.addObject("item",  conceptData );
		mv.addObject("mode", id != null ? "update" : "create");
		mv.addObject("today", ymdSDF.format(conceptData.getOrderDate() != null ? conceptData.getOrderDate() : new Date()));
		return mv;				
	}		
	
	// ?????? ?????? ?????? DB ??????
	@RequestMapping("/daily_report/register_concept")
	public @ResponseBody StandardResponse registerConceptJob(@ModelAttribute @Valid JobConceptRegForm form
																, BindingResult result
																, MultipartHttpServletRequest request
																){
		StandardResponse response = new StandardResponse();
		
		try{
			MultipartFile internalPriceFile = request.getFile("internalPriceFile");
			MultipartFile conceptFile = request.getFile("conceptFile");
			if(form.getMode() == null)
				throw new IllegalArgumentException("Mode ?????? ????????????");
			
			if(form.getMode() == RegMode.create 
						&& jobOrderService.checkOrderNoExists(JobTypeEnum.CONCEPT
																, OrderTypeEnum.parse(form.getOrderType())
																, form.getOrderNoBase()
																, form.getOrderNoExtra()) )
				throw new IllegalArgumentException("?????? ???????????? Order-No ?????????");
			
			if(form.getQuantity() == null)
				throw new IllegalArgumentException("????????? ????????? ?????????");
			
			if(result.hasErrors())
				throw new IllegalArgumentException("????????? ????????? ?????????\n" + result.toString());
						
			// ?????? ??????
			Long conceptFileNo = null, internalPriceFileNo = null;		// registeredFile ???????????? ????????? ??? ?????? ????????? 0 ?????? ?????? 
			if(conceptFile != null)
				conceptFileNo = registeredFileService.saveFile( conceptFile
																, RegisteredFileService.FileCategory.DOC
																, "CONCEPT"
																, loginDongleService.getLoginId() );
			if(internalPriceFile != null)
				internalPriceFileNo = registeredFileService.saveFile( internalPriceFile
																		, RegisteredFileService.FileCategory.DOC
																		, "CONCEPT"
																		, loginDongleService.getLoginId() );
			
			if( StringUtils.isEmpty(form.getOrderNoBase()) ){
				form.setOrderNoBase(null);
				form.setOrderNoExtra(null);
			}
			
			ConceptJobOrder data = new ConceptJobOrder();			
			if(form.getMode() == RegMode.create){
				data.setOrderType(form.getOrderType());
				data.setOrderDate(new Date());
				data.setCustomerId(StringUtils.isEmpty(form.getCustomerId()) ? null : form.getCustomerId());
				data.setOrderNoBase(form.getOrderNoBase());
				data.setOrderNoExtra(form.getOrderNoExtra());
				data.setDevice(form.getDevice());
				data.setQuantity(form.getQuantity());
				data.setBusinessUserId(form.getBusinessUserId());
				data.setDesignUserId(form.getDesignUserId());
				data.setCustomerUser(form.getCustomerUser());
				data.setNote(form.getNote());
				data.setInternalUnitPrice(form.getInternalUnitPrice());
				data.setInternalUnitPriceSharedDate(form.getInternalUnitPriceSharedDate());
				data.setEstimatedPrice(form.getEstimatedPrice());
				data.setNegotiatedPrice(form.getNegotiatedPrice());
				data.setInternalPriceFileNo(internalPriceFileNo);
				data.setConceptFileNo(conceptFileNo);
				
				log.debug("?????? ?????? ????????? ==> " + data);
				jobOrderService.addNewConcept(data);
			}else{
				data = jobOrderService.getSpecificConcept(form.getId(), OrderTypeEnum.parse(form.getOrderType()) , null);
				
				// update
				data.setCustomerId(StringUtils.isEmpty(form.getCustomerId()) ? null : form.getCustomerId());
				data.setDevice(form.getDevice());
				data.setQuantity(form.getQuantity());
				data.setBusinessUserId(form.getBusinessUserId());
				data.setDesignUserId(form.getDesignUserId());
				data.setCustomerUser(form.getCustomerUser());
				data.setNote(form.getNote());
				data.setInternalUnitPrice(form.getInternalUnitPrice());
				data.setInternalUnitPriceSharedDate(form.getInternalUnitPriceSharedDate());
				data.setEstimatedPrice(form.getEstimatedPrice());
				data.setNegotiatedPrice(form.getNegotiatedPrice());
				if(conceptFileNo != null)
					data.setConceptFileNo(conceptFileNo);
				if(internalPriceFileNo != null)
					data.setInternalPriceFileNo(internalPriceFileNo);
				data.setLastModifiedUserId(loginDongleService.getLoginId());
				jobOrderService.modifyConcept(data);
				
				// ?????? ??????
				jobOrderService.makeConceptFinished(form.getId(), loginDongleService.getLoginId());
			}
			
			response.setResult("OK");
			
		}catch(IllegalArgumentException e){
			response.setResult("ERROR");
			response.setReason(e.getMessage());
			log.error("?????? ?????? ??? ???????????? ??????. ???????????? = " + form, e);			
		}catch(Exception e){
			response.setResult("ERROR");
			response.setReason("?????? ??? ????????? ?????????????????????");
			log.error("?????? ?????? ??? ??????. ???????????? = " + form, e);
		}
		
		return response;
	}
	
	// ??????, ????????? ??????
	@RequestMapping("/daily_report/update/job")
	@ResponseBody
	public StandardResponse updateJob( JobOrder requested, String requestedAction ){
		StandardResponse res = null;
		try{
			
			JobOrder order = jobOrderService.searchJob(requested.getId());
			if(order == null)
				throw new IllegalArgumentException("????????? ??????/????????? ?????? ?????? ??? ??????");
			
			if(requestedAction.equals("edit")){
				// ??????
				jobOrderService.modifyJob(requested, loginDongleService.getLoginId());
				noticeMessageService.addRegisteredNotice(order.getId(), "G", "????????????", "??????????????? ?????????????????????.", order.getDevice(), noticeMessageService.getCustomerNameToNotice(order.getId()));
				pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(order.getId())+"]", "Order Config");
			}else if(requestedAction.equals("delete")){
				// ?????? ??????
				jobOrderService.deleteJob(order.getId(), loginDongleService.getLoginId());
				noticeMessageService.addRegisteredNotice(order.getId(), "G", "????????????", "??????????????? ?????????????????????.", order.getDevice(), noticeMessageService.getCustomerNameToNotice(order.getId()));
				pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(order.getId())+"]", "Order Deleted");
			}else if(requestedAction.equals("finish")){
				// ?????? ??????
				jobOrderService.makeJobFinished(order.getId(), loginDongleService.getLoginId());
				noticeMessageService.addRegisteredNotice(order.getId(), "G", "????????????", "??????????????? ?????????????????????.", order.getDevice(), noticeMessageService.getCustomerNameToNotice(order.getId()));
				pushAlarmService.orderStartService("["+jobOrderService.selectJobOrderFromPush(order.getId())+"]", "Order Complet");
			}else{
				throw new IllegalArgumentException("[" + requestedAction + "]??? ???????????? ?????? ???????????????");
			}
			
			res = StandardResponse.createResponse("OK");
			
		}catch(IllegalArgumentException e){
			log.error("DB ?????? ??? ?????? - ???????????? ??????", e);
			res = StandardResponse.createResponse("ERROR", e.getMessage());
		}catch(Exception e){
			log.error("DB ?????? ??? ??????", e);
			res = StandardResponse.createResponse("ERROR", "DB ?????? ??? ?????? ??????");
		}
		return res;
	}
	
	// ?????? ??????, ????????? ?????? '??????'
	@RequestMapping("/daily_report/update/concept")
	@ResponseBody
	public StandardResponse updateConcept(String orderNo, Long id, 
											String device, 
											String internalUnitPriceSharedDate, String orderDate,
											Integer quantity, String customerUser, String note,
											String businessUserId, String designUserId,
											Long internalPriceFileNo, Long conceptFileNo,
											ConceptRequestedAction requestedAction){
		StandardResponse res = null;
		try{
			Date parsedInternalUnitPriceSharedDate = null, parsedOrderDate = null;
			
			if(orderNo == null || id == null)
				throw new InvalidParameterException("????????? ????????? ???????????? ???????????????");
			
			log.debug("???????????? ??? ?????? ??????. ID=" + id + ", orderNo = " + orderNo);
			
			ConceptJobOrder concept = jobOrderService.getSpecificConcept(id);
			if(concept == null){
				log.error("????????? ?????? ????????? ?????? ??? ??????. ID = " + id + ", orderNo = " + orderNo);
				throw new InvalidParameterException("????????? ?????? ????????? ?????? ??? ????????????.");
			}

			if(!StringUtils.isEmpty(internalUnitPriceSharedDate)) {
				try{
					parsedInternalUnitPriceSharedDate = sdf.parse(internalUnitPriceSharedDate);
				}catch(Exception e){
					throw new InvalidParameterException("?????? ????????? ?????? ????????? ?????????????????????. [" + internalUnitPriceSharedDate + "]" );
				}
			}
			if(!StringUtils.isEmpty(orderDate)) {
				try{
					parsedOrderDate = sdf.parse(orderDate);
				}catch(Exception e){
					throw new InvalidParameterException("???????????? ????????? ?????????????????????. [" + parsedOrderDate + "]" );
				}
			}
			
			concept.setDevice(device);
			concept.setInternalUnitPriceSharedDate(parsedInternalUnitPriceSharedDate);
			concept.setQuantity(quantity);
			concept.setCustomerUser(customerUser);		// ????????? ?????? ??????&??????????????? customerUser ??? ???????????? ??????
			concept.setNote(note);
			concept.setLastModifiedUserId( loginDongleService.getLoginId() );
			concept.setBusinessUserId(businessUserId);
			concept.setDesignUserId(designUserId);
			concept.setOrderDate(parsedOrderDate);
			if(internalPriceFileNo != null){
				log.debug("??????[" + id + "] ??? ?????? ?????? ????????? [" + internalPriceFileNo + "] ??? ??????" );
				concept.setInternalPriceFileNo(internalPriceFileNo);
			}
			if(conceptFileNo != null){
				log.debug("??????[" + id + "] ??? ?????? ?????? ????????? [" + conceptFileNo + "] ??? ??????" );
				concept.setConceptFileNo(conceptFileNo);
			}
						
			jobOrderService.modifyConcept(concept);
			
			switch(requestedAction){
			case	edit:
				// ???????????? ????????? ?????????
				break;
				
			case delete:
				jobOrderService.deleteConcept(id, loginDongleService.getLoginId());
				break;
				
			case finish:
				if(concept.getConceptFileNo() == null)
					throw new InvalidParameterException("???????????? ????????? ????????? ?????????");
				
				jobOrderService.makeConceptFinished(id, loginDongleService.getLoginId());
				break;
			case A:
				concept.setCurrentStage("A");
				jobOrderService.modifyConcept(concept);
				break;
			case B:
				concept.setCurrentStage("B");
				jobOrderService.modifyConcept(concept);
				break;
			case C:
				concept.setCurrentStage("C");
				jobOrderService.modifyConcept(concept);
				break;
			case D:
				concept.setCurrentStage("D");
				jobOrderService.modifyConcept(concept);
				break;
			case E:
				concept.setCurrentStage("E");
				jobOrderService.modifyConcept(concept);
				break;
			default:
				throw new InvalidParameterException("[" + requestedAction + "] ???????????? ?????? ???????????????");
			}
			
			res = StandardResponse.createResponse("OK");
		}catch(InvalidParameterException e){
			res = StandardResponse.createResponse("ERROR", e.getMessage());
			log.error("?????? ?????? ?????? ??? ?????? - " + e.getMessage());
		}catch(Exception e){
			log.error("?????? ?????? ?????? ??? ?????? ", e);
			res = StandardResponse.createResponse("ERROR", "?????? ?????? ?????? ??? ??????");
		}
		return res;
	}
	
	// ????????? ?????? ??????
	@RequestMapping("/daily_report/popup/edit_estimation")
	public ModelAndView popupEstimationEdit(Long jobId){
		
		JobOrder job = jobOrderService.searchJob(jobId);
		if(job == null){
			log.info("????????? ????????? ?????? ?????? ?????? ????????? ?????? ??? ??????. ID=" + jobId);
			return null;
		}
		
		log.debug("??????[" + jobId + "]??? ????????? " + job.getOrderType() + ", isJig = " + job.isJigJob());
		ModelAndView mv = new ModelAndView(job.isJigJob() ? "daily_report/list_sub/popup_jig_estimation_edit" : "daily_report/list_sub/popup_job_estimation_edit");
		mv.addObject("jobId", jobId);
		mv.addObject("data", job);
		return mv;
	}
	
	// ????????? ??????
	@RequestMapping("/daily_report/update/estimation")
	@ResponseBody
	public StandardResponse updateEstimation(JobOrder order){
		
		StandardResponse res = null;
		try{
			
			if(order.getId() == null)
				throw new IllegalArgumentException("id??? ??????");
			
			if(StringUtils.isEmpty(order.getDevice()))
				throw new IllegalArgumentException("device??? ??????"); 
			
			if(StringUtils.isEmpty(order.getBusinessUserId()))
				throw new IllegalArgumentException("?????? ???????????? ????????? ?????????");
			
			jobOrderService.updateJobEstimation(order, loginDongleService.getLoginId());
			
			res = StandardResponse.createResponse("OK");
			
		}catch(IllegalArgumentException e){
			res = StandardResponse.createResponse("ERROR", e.getMessage());
		}catch(Exception e){
			res = StandardResponse.createResponse("ERROR", "????????? ?????? ??? ??????");
			log.error("????????? ?????? ??? ??????", e);
		}		
		
		return res;
	}	
	
	@RequestMapping("/daily_report/api/modification_list")
	@ResponseBody
	public StandardResponse apiDailyReportModification(Long id){
		
		StandardResponse res = null;
		try{
			
			res = StandardResponse.createResponse("OK");
			res.setData( jobOrderChangeHistoryService.loadChangeHistory(id) );
			
		}catch(Exception e){
			e.printStackTrace();
			res = StandardResponse.createResponse("ERROR", e.getMessage());
		}
		return res;
	}
}
