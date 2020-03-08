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
	   
  	
	    	var jobPurchaseId = $("#jobPurchaseId").val();
	    	var id = $("#id").val();
	    	
	    
	    	
	        if(confirm("인수취소 하시겠습니까?")){
	        	document.form1.target="parent";
	        	document.form1.action = "/transition/purchaseDeleteAct";
	        	document.form1.submit();
	        	self.close();
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
        	<h1>인수대기 목록</h1>
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
                                        <col style="width:14%;" />
                                        <col style="width:15%;" />
                                        <col style="width:20%;" />
                                        <col style="width:20%;" />
                                        <col style="width:13%;" />
                                        <col style="width:13%;" />
                                     
                                    
                                        </colgroup>
                                        <tr>
                                            
                                            <th>선택</th>
                                            <th>인수부서</th>
                                            <th>OderNo</th>
                                            <th>품목</th>
                                            <th>Model No/Size</th>
                                            <th>입고수량</th>                                           
                                            <th>인계자</th>
     
                                            
                                        </tr>
                                        <c:set var="No" value="${pageNo + 1}"></c:set>
                                        <c:forEach items="${data}" var="line">
                                        
                                      

                                        <tr id="${No}">
                                        
                                            
                                            <td><input type="checkbox" name="record" id="record${No}"></td>
                                            <td>${line.deptName}</td>
                                           	<td>${line.jobOrderNo}</td>
                                           	<td>${line.description}</td>
                                            <td>${line.modelNo}</td>                                
											<td>${line.warehousingQuantity}</td>
                                           	<td>${line.userName}</td>
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
        	
            <div class="popBtn">

            	<button class="btn_blue" type="button" id="UpdateBtn">남은 목록 인수 취소</button> 
              	<button class="btn_blue" type="button" id="delete">선택 된 LIST 제외</button>
            </div>
        
        
        
	</div>


</div>
</form>
</body>
</html>