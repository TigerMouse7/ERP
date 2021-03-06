<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"	prefix="layout"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="ko-KR">
<head>
	<meta charset="utf-8">
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    
	<link rel="stylesheet" type="text/css" href="/css/style_pop.css">

    <script language="JavaScript" src="/js/menu.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
  <script type="text/javascript">
  
  window.name="parent2";
  
  
  
  $(document).ready(function(){
	  
	  $("#ApplyAll").click(function(e){
			e.preventDefault();
			
			var val = $("#GlobalDate").val();
			console.log("date is " + val);
			
			$("input[name='receiveDate']").each(function(){
				$(this).val(val);	
			});
		});
	  
	  $("#BoxApplyAll").click(function(e){
			e.preventDefault();
			
			var count = document.getElementsByName("issueType").length
			
			for(var i=1; i<=count; i++){
				
				var sel = document.getElementById("issueType"+i);
				sel[1].selected = true;	
		 
		    }
			
		
		});
	  
	  $("#delete").on("click", function() {

	        var chk = $("input[name='record']:checked").length;

	        if(chk > 0) {

	            $("input[name='record']:checked").each(function() {
	            	
	            	
	            	var trid = $(this).closest('tr').attr('id');
	    	    	
	    			$(".jobPurchaseId"+trid).remove();
	    			$(".stockId"+trid).remove();
	    			$(".requestId"+trid).remove();
	    			
	                $(this).closest("tr").remove();

	            });

	        } else {

	            alert("????????? ?????? ??????");

	        }

	    });
	  
	$("#allChex").click(function(){
	    	
  			for(i=0; i < form1.record.length; i++) {
  				form1.record[i].checked = true;
  			}
	    
	      
	    });
	  
	  
	  
	    $("#UpdateBtn").click(function(){
	   
  	
	    	var selectPartnerName = $("#selectPartnerName option:selected").val();
	    	var rawJobOrderId = $("#rawJobOrderId").val();
	    	var requestId = $("#requestId").val();
	    	var jobPurchaseId = $("#jobPurchaseId").val();
	    	var EmodelNo =  $("#EmodelNo").val();
			var receiveDate =  $("#receiveDate").val();
			var issuePrice =  $("#issuePrice").val();
			var orderQuantity =  $("#orderQuantity").val();
			var issueType = $("#issueType option:selected").val();
			var maker =  $("#maker").val();
			var stockId =  $("#stockId").val();
			var stockUseQuantity =  $("#stockUseQuantity").val();
			
	    	
	        if(confirm("????????? ?????? ????????? ?????????????????????? ??????/??????????????? ????????? ?????? ?????? ???????????? ???????????????.")){
	        	document.form1.action = "/mail/purchasenew/issue";
	        	document.form1.submit();
	        }
	    });
	   
	});	
  

	
  </script>

</head>

<body class="sub">
<form id="form1" name="form1" method="post">
<div id="container">



	<div class="popForm">
    	
        <!-- ?????? ????????? -->
        <div class="popTitArea">
        	<h1>??????</h1>
        </div>
        <!-- //?????? ????????? -->
    
       <c:forEach items="${data}" var="item">
                <!-- ????????? ????????? -->
                <div class="popList">
                    <div class="popListTit">
                       <h4>Order No : [${item[0].jobOrderNo }]</h4>
                       
                    </div>
                    
                  
                
        	<!-- ????????? ????????? -->
            <div class="listArea">
            
      
                    	 <select name="selectPartnerName" id="selectPartnerName" style="width:263px;">
                         	
                            	<c:forEach items="${partnerName}" var="name">
                                	<option value="${name}"<c:if test="${name == onePartnerName}">selected</c:if>>${name}</option>
                               	</c:forEach>
                         </select>
                         
                         <span>
                    		<input type="date" style="width: 180px" id="GlobalDate" >
                    	</span>
                		<span>
                    		<a href="#" class="btn_line_gray" id="ApplyAll">????????? ?????? ??????</a>
                    	</span>
                    	
                    	<span>
                    		<a href="#" class="btn_line_gray" id="BoxApplyAll">???????????? ?????? ??????</a>
                    	</span>
                    
                
                        <!-- ????????? -->
                        <div class="listBox">
                        	  <table>
                                        <caption> </caption>
                                        <colgroup span="2">
      
                                        <col style="width:3%;" />
                                        <col style="width:6%;" />
                                        <col style="width:5%;" />
                                        <col style="width:3%;" />
                                        <col style="width:5%;" />
                                        <col style="width:12%;" />
                                        <col style="width:12%;" />
                                        <col style="width:5%;" />
                                        <col style="width:5%;" />
                                        <col style="width:5%;" />
                                        <col style="width:5%;" />
                                        <col style="width:3%;" />   
                                        <col style="width:3%;" />
                                        <col style="width:3%;" />
                                        <col style="width:3%;" />
                                        <col style="width:6%;" />
                                        <col style="width:6%;" />
                                        <col style="width:10%;" />
                                    
                                        </colgroup>
                                        <tr>
                                            
                                            <th>??????</th>
                                            <th>seq</th>
                                            <th>OderNo</th>
                                            <th>UNIT</th>
                                            <th>??????</th>
                                            <th>(??????)Model No/Size</th>
                                            <th>(??????)Model No/Size</th>
                                            <th>Maker</th>
                                            <th>?????????</th>
                                            <th>?????????</th>
                                            <th>?????????</th>                                       
                                            <th>??????????????????</th>
                                            <th>????????????</th>
                                            <th>??????????????????</th>                                           
                                            <th>????????????</th>
                                            <th>????????????</th>
                                            <th>?????????</th>
                                            <th>?????????</th>
                                          
                                            
                                        </tr>
                                        <c:set var="No" value="${pageNo + 1}"></c:set>
                                        <c:forEach items="${item}" var="line">
                                        
                                        <fmt:parseNumber var="Sum" value="${line.quantity+line.spare}" integerOnly="true"/>

                                        <tr id="${No}">
                                        
                                            
                                             <td><input type="checkbox" name="record" id="record${No}"></td>
                                            <td>${line.id}</td>
                                            <td>${line.jobOrderNo}</td>
                                           	<td>${line.unitNo}</td>
                                           	<td>${line.description}</td>
                                            <td><a href="javascript:PopWin('/purchase/stockUse/${line.modelNo}/${No}','1300','500','no');" class="btn_line_blue">${line.modelNo}</a></td>                                
                                            <td><input type="text" id="EmodelNo" name="EmodelNo" value="${line.modelNo}"></td>
                                           	<td><input type="text" id="maker" name="maker" value="${line.maker}"></td>
											<td><fmt:formatNumber value="${line.min}" pattern="#,###" /></td>
											<td><fmt:formatNumber value="${line.max}" pattern="#,###" /></td>
											<td><fmt:formatNumber value="${line.minestimatedPrice}" pattern="#,###" /></td>
                                           	<td>${Sum}</td>
                                           	<td><input type="number" id="stockQuantity" name="stockQuantity" value="${line.stockqty}" readonly></td>
                                           	<td><input type="number" id="stockUseQuantity${No}" name="stockUseQuantity" value="${line.stockUseQuantity}" readonly></td>
                                                         
                                           	<td><input type="number" id="orderQuantity${No}" name="orderQuantity" value="${Sum}"></td>
                                           	<td>
                                           		<select name="issueType" id="issueType${No}" style="width:80px;">
                                           			<option value="B" selected>??????</option>
                                           			<option value="B">??????????????????</option>
                                           			<option value="C">????????????(??????)</option>
                                           			<option value="H">???????????????</option>
                                           		</select>
                                           	</td>                       
                                           	<td><input type="number" id="issuePrice" name="issuePrice" value=0></td>  
                                           	<c:choose>
                                           		<c:when test="${line.roundRobinYN eq 'Y'}">
                                           			<td><input type="date" id="receiveDate" name="receiveDate" 
            										value="<fmt:formatDate value="${line.robinRequestDate}" pattern="yyyy-MM-dd"/>"></td>
                                           		</c:when>
                                           		<c:otherwise>
                                           			<td><input type="date" id="receiveDate" name="receiveDate" 
            										value="<fmt:formatDate value="${line.receiveDate}" pattern="yyyy-MM-dd"/>"></td>
                                           		
                                           		</c:otherwise>
                                           	</c:choose>
                                           	
                                           	
                                      
                                        </tr>
                                        <input type="hidden" id="jobPurchaseId" name="jobPurchaseId" class="jobPurchaseId${No}" value="${line.id}">
                                        <input type="hidden" id="stockId${No}" name="stockId" class="stockId${No}" value=" ">
                                        <input type="hidden" id="requestId" name="requestId" class="requestId${No}" value="aa">
                                        
                                        <c:set var="No" value="${No + 1 }" />
                                        </c:forEach>
                                    </table>
                                    
                                    <input type="hidden" id="partnerId" name="partnerId" value="${partnerId}">
                                    <input type="hidden" id="rawJobOrderId" name="rawJobOrderId" value="${rawJobOrderId}">
                                    
                        </div>
                        <!-- //????????? -->
                        
                    </div>
                    <!-- //????????? ????????? -->
            <!-- //????????? ????????? -->

                
            
        </div>
        </c:forEach>
        <!-- //?????? ????????? -->
        
            <div class="popBtn">
				<!-- 
            	<button class="btn_blue" type="button" id="UpdateBtn">??????</button>
            	<button class="btn_blue" type="button" id="allChex">????????????</button>  
              	<button class="btn_blue" type="button" id="delete">?????? ??? LIST ?????????</button>
              	 -->
              	<a href="#" class="btn_blue" id="UpdateBtn">??????</a>
              	<a href="#" class="btn_blue" id="allChex">????????????</a>
              	<a href="#" class="btn_gray" id="delete">?????? ??? LIST ?????????</a>
            </div>
        
        
        
	</div>


</div>
</form>
</body>
</html>
