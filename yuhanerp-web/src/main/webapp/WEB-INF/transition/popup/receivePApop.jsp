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
	  
	  $("#delete").on("click", function() {

	        var chk = $("input[name='record']:checked").length;

	        if(chk > 0) {

	            $("input[name='record']:checked").each(function() {
	            	
	            	
	            	var trid = $(this).closest('tr').attr('id');
	    	
	    			$(".jobPurchaseId"+trid).remove();
	    			$(".id"+trid).remove();


	                $(this).closest("tr").remove();

	            });

	        } else {

	            alert("삭제할 항목 없음");

	        }

	    });
	  
	  
	  
	    $("#UpdateBtn").click(function(){
	   
  	
	    	var selectUserName = $("#selectUserName option:selected").val();
	    	var jobPurchaseId = $("#jobPurchaseId").val();
	    	var id = $("#id").val();
	    	var selectReceive = $("#selectReceive option:selected").val();

	    	
	    
	    	
	        if(confirm("인수확인을 하겠습니까? 일반인계(재고등록), 불량/파손(재구매등록)")){
	        	document.form1.target="parent";
	        	document.form1.action = "/transition/purchaseReciveAct";
	        	document.form1.submit();
	        	self.close();
	        }
	    });
	    
	    
	    $("#allChex").click(function(){
		    	
	  			for(i=0; i < form1.record.length; i++) {
	  				form1.record[i].checked = true;
	  			}
		    
		      
		});
	   
	});	
  

	
  </script>

</head>

<body class="sub">
<form id="form1" name="form1" method="post">
<div id="container">



	<div class="popForm">
    	
        <!-- 팝업 타이틀 -->
        <div class="popTitArea">
        	<h1>인수인계 확인</h1>
        </div>
        <!-- //팝업 타이틀 -->
    
   
                
        	<!-- 테이블 콘텐츠 -->
            <div class="listArea">
            
      
                
                        <!-- 게시판 -->
                        <div class="listBox">
                        	  <table>
                                        <caption> </caption>
                                        <colgroup span="2">
      
                                        <col style="width:5%;" />
                                        <col style="width:10%;" />
                                        <col style="width:10%;" />
                                        <col style="width:14%;" />
                                        <col style="width:20%;" />
                                        <col style="width:10%;" />
                                        <col style="width:11%;" />
                                        <col style="width:20%;" />
                                        <col style="width:10%;" />
                                     
                                    
                                        </colgroup>
                                        <tr>
                                            
                                            <th>선택</th>
                                            <th>인수부서</th>
                                            <th>OderNo</th>
                                            <th>품목</th>
                                            <th>Model No/Size</th>
                                            <th>입고수량</th>                                           
                                            <th>인계자</th>
                                            <th>인계사유</th>
                                            <th>인수방법</th>
     
                                            
                                        </tr>
                                        <c:set var="No" value="${pageNo + 1}"></c:set>
                                        <c:forEach items="${data}" var="line">
                                        
                                      

                                        <tr id="${No}">
                                        
                                            
                                            <td><input type="checkbox" name="record" id="record${No}"></td>
                                            <td>${line.deptName}</td>
                                           	<td>${line.jobOrderNo}</td>
                                           	<td>${line.description}</td>
                                            <td>${line.modelNo}</td>                                
											<td>${line.quantity}</td>
                                           	<td>${line.userName}</td>
                                           	<c:choose>
                                           		<c:when test="${line.kindRepass eq 'P'}">
                                           			<td style="color:red;">불량/파손</td>
                                           		</c:when>
                                           		<c:otherwise>
                                           			<td>일반인계</td>
                                           		</c:otherwise>
                                           		
                                           	</c:choose>
                                           	
                                           	<td>
                                           		 <select name="selectReceive" id="selectReceive" style="width:263px;">
                    								<option value="A" selected>재고등록</option>
                    								<option value="M">일반인계</option>
                    								<option value="P">재구매등록</option>
                   		
                 								</select>
                                           		
                                           	
                                           	</td>
                                           	
                                        </tr>
                                        <input type="hidden" id="jobPurchaseId" name="jobPurchaseId" class="jobPurchaseId${No}" value="${line.jobPurchaseId}">
                                        <input type="hidden" id="id" name="id" class="id${No}" value="${line.id}">
                                        
                            
                                        
                                        <c:set var="No" value="${No + 1 }" />
                                        </c:forEach>
                                    </table>
                                    
                   
                                    
                        </div>
                        <!-- //게시판 -->
                        
                    </div>
                    <!-- //테이블 콘텐츠 -->
            <!-- //테이블 콘텐츠 -->

                
            
        </div>

        <!-- //팝업 콘텐츠 -->
        
        	</br>
        	</br>
        	<div>
        		<span>
        			인수자 : 
        		</span>
        		
        		<span>
             	 <select name="selectUserName" id="selectUserName" style="width:263px;">
                    <option value="">선택</option>
                   	<c:forEach items="${userName}" var="name">
                    	<option value="${name}">${name}</option>
                    </c:forEach>
                 </select>
                 </span>
                    
        	
        	
        	</div>
        	
        	</br>
        	</br>
            <div class="popBtn">

            	<button class="btn_blue" type="button" id="UpdateBtn">인수 확인</button>
            	<button class="btn_blue" type="button" id="allChex">전체선택</button>  
              	<button class="btn_blue" type="button" id="delete">선택 된 LIST 제외</button>
            </div>
        
        
        
	</div>


</div>
</form>
</body>
</html>