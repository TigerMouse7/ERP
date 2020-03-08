<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko-KR">
<head>
	<base href="/" />
	<meta charset="utf-8">
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="css/style_pop.css">
    <link rel="stylesheet" type="text/css" href="css/multi.css">

	<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/menu.js"></script>
	<script type="text/javascript" src="/js/auto_datepicker.js"></script>
    <script type="text/javascript" src="/js/ux.js"></script>
    <!-- 트리메뉴 -->
    <script type="text/javascript" src="js/checktree.js"></script>
    <script type="text/javascript">
		var checkmenu = new CheckTree('checkmenu');
	</script>
    <!-- //트리메뉴 -->
</head>

<body class="sub">
<div id="container">

	<div class="popForm">
        <!-- 팝업 타이틀 -->
        <div class="popTitArea">
        	<h1>검사완료 생산관리로 인계</h1>
        </div>
        <!-- //팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="popCont">
            
            <!-- 설명 -->
            <div class="popText" >
            	<!-- 항목1 -->
            	인수자와 인계자 정보를 입력하시기 바랍니다.<br/>
                
                <!-- 항목2 -->
                <!--
                취소 사유를 입력하시오.
				-->
            </div>
            <!-- //설명 -->
            
            <!-- 테이블 콘텐츠 -->
            <div class="popList">

                <!-- 폼 영역 -->
                <div class="popBoard">
                   	<table>
                       	<caption> </caption>
                        <col style="width:120px;" />
                        <col style="width:120px;" />
                       	<thead>
                           	<tr>
	                           <th>인계자(전달자)</th>
	                           <th>인수자(받는자)</th>
                           </tr>
                        </thead>
                        <tbody>
                        	<tr>
                        		<td>
                                <c:import url="/internal/util/select/qcuser">
	                               	<c:param value="fromUser" name="controlName"/>
	                               	<c:param value="true" name="showUnspecifiedItem"/>
	                               	<c:param value="w100" name="cssClass" />
                                </c:import>
                        		</td>
                        		<td>
                                <c:import url="/internal/util/select/manager">
	                               	<c:param value="toUser" name="controlName"/>
	                               	<c:param value="true" name="showUnspecifiedItem"/>
	                               	<c:param value="w100" name="cssClass" />
                                </c:import>
                        		</td>
                        	</tr>
                        </tbody>
                    </table>
                </div>
                <!-- //폼 영역 -->
                
                <!-- 팝업 버튼 -->
                <div class="popBtn">
                    <a href="#" id="CloseWindowButton" class="btn_gray">취소</a>
                    <a href="#" id="SaveButton" class="btn_blue">인계</a>
                </div>
                <!-- //팝업 버튼 -->
            
            </div>
            <!-- //테이블 콘텐츠 -->
            
        </div>
        <!-- //팝업 콘텐츠 -->

	</div>
    
<!-- 멀티셀렉트 -->
<script src='js/multiselect03.js'></script>
<script src='js/multiselect02.js'></script>
<script src="js/multiselect01.js"></script>  
<!-- //멀티셀렉트 -->
</div>

	<script type="text/javascript">
	
		$(document).ready(function(){
			
			$("#SaveButton").click(function(e){
				
				var fromId = $("#fromUser").val();
				var toId = $("#toUser").val();

				opener.${callback}("${key}", "${dwkey}", fromId, toId );
				window.close();
			});
		});
	
	</script>

</body>
</html>
