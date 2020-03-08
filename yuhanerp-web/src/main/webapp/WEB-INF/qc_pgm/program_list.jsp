<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"	prefix="layout"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<layout:extends name="../layout/MctMaster.jsp">

	<layout:put block="mainbody">
                <!-- 서브 타이틀 -->
                <div class="titArea">
                    <ul class="navi">
                        <li><span class="icon_home">홈</span></li>
                        <li>가공&QC&조립</li>
                        <li>프로그램/배선 현황</li>
                    </ul> 
                </div>
                <!-- 서브 타이틀 -->
                
                <!-- 서브 콘텐츠 -->
                <div class="contArea">
                    <!-- 테이블 콘텐츠 -->
                    
                	<!-- 테이블 타이틀 -->

                    <!-- //테이블 타이틀 -->
                    
                    <div class="listArea">
                    	<!-- 타이틀 -->
                    	<div class="listTit">
                        	<h3>설비 진행</h3>
                        </div>
                        <!-- //타이틀 -->
                        
                        <!-- 게시판 -->
                        <div class="listBox">
                        	<table>
                            	<caption> </caption>
                                <colgroup span="2">
                                <col style="width:100px;" />
                                <col style="width:100px;" />
                                <col style="width:80px;" />
                                <col style="width:200px;" />
                                <col style="width:150px;" />
                                <col style="width:50px;" />
                                <col style="width:70px;" />
                                <col style="width:70px;" />
                                <col style="width:70px;" />
                                <col style="width:70px;" />
                     <!--	    <col style="width:70px;" />	-->
                                <col style="width:70px;" />
                                <col style="width:60px;" />
                                <col style="width:60px;" />
                                </colgroup>
                            	<thead>
                                	<tr>
                                    	<th>Order No</th>
                                    	<th>업체</th>
                                        <th>출고일</th>
                                        <th>Device</th>
                                        <th>비고</th>
                                        <th>수량</th>
                                        <th>설계</th>
                                        <th>가공</th>
                                        <th>구매</th>
                                        <th>조립</th>
                    <!--	            <th>베선</th>		-->
                                        <th>프로그램</th>
                                        <th>상태</th>
                                        <th>진행률</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="jobitem" items="${jobData}">
                                	<tr data="${jobitem.id }">
                                    	<td>${jobitem.orderFullNo }</td>
                                    	<td>${jobitem.customerName }</td>
                                        <td>
                                        	<c:choose>
                                        		<c:when test="${jobitem.currentStage eq 'A' }">
                                        			<fmt:formatDate pattern = "yy.MM.dd" value = "${jobitem.shippingDate }" />
                                        		</c:when>
                                        		<c:when test="${jobitem.currentStage eq 'H' }">
                                        			<fmt:formatDate pattern = "yy.MM.dd" value = "${jobitem.shippingDate }" />
                                        		</c:when>
                                        		<c:otherwise>
                                        			<fmt:formatDate pattern = "yy.MM.dd" value = "${jobitem.deliveryDate }" />
                                        		</c:otherwise>
                                        	</c:choose>
                                        </td>
                                    	<td>${jobitem.device }</td>
                                    	<td>${jobitem.note }</td>
                                    	<td>${jobitem.quantity }</td>
                                    	<td>${jobitem.designUserName }<br>${jobitem.designProgress }%</td>
                                    	<td>
                   							<a href="#" class="RowJobMctView">가공품<br>${ jobitem.GetProcessProg() }%</a>
                                    	</td>
                                    	<td>
                   							<a href="#" class="RowJobPurView">구매품<br>${ jobitem.GetPurchaseProg() }%</a>
                                    	</td>
                                    	<td>${jobitem.assemblyName }<br>${jobitem.assemblyProgress }%</td>
                      <!--	            <td>${jobitem.wiringName }</td>		-->
                                    	<td>${jobitem.pgmName }</td>
                                        <td>
                                        	<c:choose>
                                       		<c:when test="${jobitem.programStatus eq 'F' }">
                                       			완료<br>
                                       			<span class="icon_check">아이콘</span>
                                       		</c:when>
											<c:otherwise>
	                                        	<c:choose>
	                         						<c:when test="${isupdate eq 'Y'}">
	                         							<a href="#" class="RowJobAction">
	                         						</c:when>
	                         					</c:choose>
	                                        	<c:choose>
	                                        		<c:when test="${jobitem.programStatus eq 'I' }">
	                                        			<a href="#" class="RowJobAction">진행중<br>
	                                        			<span class="icon_play">아이콘</span></a>
	                                        		</c:when>
	                                        		<c:when test="${jobitem.programStatus eq 'H' }">
	                                        			<a href="#" class="RowJobAction">홀딩<br>
	                                        			<span class="icon_lock">아이콘</span></a>
	                                        		</c:when>
	                                        		<c:when test="${jobitem.programStatus eq 'S' }">
	                                        			<a href="#" class="RowJobAction">중단<br>
	                                        			<span class="icon_stop">아이콘</span></a>
	                                        		</c:when>
	                                        		<c:otherwise>
	                                        			<a href="#" class="RowJobAction">대기<br>
	                                        			<span class="icon_play2">아이콘</span></a>
	                                        		</c:otherwise>
	                                        	</c:choose>
	                                        	<c:choose>
	                         						<c:when test="${isupdate eq 'Y'}">
	                         							</a>
	                         						</c:when>
	                         					</c:choose>
	                         				</c:otherwise>
	                         				</c:choose>
                                        </td>
                                        <td>
                                        	<c:if test="${(jobitem.programStatus eq 'I') and (isupdate eq 'Y')}">
                                        	<a href="#" class="RowProgress">${jobitem.programProgress }%</a>
                                        	</c:if>
                                        	<c:if test="${jobitem.programStatus ne 'I'}">
                                        	${jobitem.programProgress }%
                                        	</c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- //게시판 -->
                        
                    </div>
                    <!-- //테이블 콘텐츠 -->

                </div>
                <!-- //서브 콘텐츠 -->
	</layout:put>
	<layout:put block="BodyScriptBlock2">
		<script type="text/javascript">

		function afterInputStatus(keyVal, atypeVal, setUser, text, messageTxt){
			var suc_url = "/qcPgm/programList";

			$.post( "/qcPgm/program_status", {
			    key: keyVal,
			    setuser: setUser,
			    atype: atypeVal,
			    reason: text
			}, function(jqXHR) {
				alert( messageTxt );
			    location.href=suc_url;
			}, 'json' /* xml, text, script, html */)
			.fail(function(jqXHR) {
			    alert( "상태 업데이트 오류" );
			});
		}

		function afterInputDelieve(keyVal, dwkeyVal, fromId, toId ){
			var suc_url = "/qcPgm/programList";

			$.post( "/qcPgm/qc_delivery", {
			    key: keyVal,
			    dwkey: dwkeyVal,
			    fromid: fromId,
			    toid: toId
			}, function(jqXHR) {
			    alert( "인수인계 처리가 완료되었습니다." );
			    location.href=suc_url;
			}, 'json' /* xml, text, script, html */)
			.fail(function(jqXHR) {
				alert( "인수인계 처리에 오류가 발생했습니다." );
			});
		}
		
		$(document).ready(function(){
			
		    $('.icon_play2').bind("mouseover", function(){ 
			     var color = $(this).css("background-color"); 
			     $(this).css("background", '#50b340  url(../images/icon_play.png) no-repeat center'); 
			     $(this).bind("mouseout", function(){ 
			     $(this).css("background", '#909090 url(../images/icon_play.png) no-repeat center'); 
			     }) 
		    });

			// 홀드 버튼 클릭
			$(".RowJobAction").click(function(e){
				e.preventDefault();
				
				var TR = $(this).parent().parent();
				var key = TR.attr("data");
				PopWin('/qcPgm/popup/program_status?callback=afterInputStatus&key=' + key,'600','330','no');
			});
			
			// 홀드 버튼 클릭
			$(".RowProgress").click(function(e){
				e.preventDefault();
				
				var TR = $(this).parent().parent();
				var key = TR.attr("data");
				PopWin('/qcPgm/popup/program_progress?key=' + key,'400','230','no');
			});
			// 홀드 버튼 클릭
			$(".RowJobMctView").click(function(e){
				e.preventDefault();
				
				var TR = $(this).parent().parent();
				var key = TR.attr("data");
				PopWin('/qcPgm/popup/process_list?key=' + key,'1800','760','yes');
			});
		});

		</script>
 
	</layout:put>
</layout:extends>