<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                        <!-- 5타이틀 -->
                    	<div class="listTit">
                        	<h3>치공구 Concept 진행 및 예정</h3>
                        </div>
                        <!-- //타이틀 -->
                        
                        <!-- 게시판 -->
                        <div class="listBox">
                        	<table>
                            	<caption> </caption>
                                <colgroup span="2">
                                <col style="width:80px;" />
                                <col style="width:100px;" />
                                <col style="width:190px;" />
                                <col style="width:60px;" />
                                <col style="width:60px;" />
                                
                                <col style="width:60px;" />
                                <col style="width:60px;" />
                                <col style="width:60px;" />
                                <col style="width:60px;" />
                                <col style="width:250px;" />
                                </colgroup>
                            	<thead>
                                	<tr>
                                        <th>Order No</th>
                                        <th>업체</th>
                                        <th>Device</th>                                        
                                        <th>컨셉완료일</th>
                                        <th>수량</th>
                                        
                                        <th>영업</th>
                                        <th>설계</th>
                                        <th>고객<br/>담당</th>
                                        <th>등록일</th>
                                        <th>비고(진행 정보)</th>
                                    </tr>
                                </thead>
                                
                                <tbody>
                                	<c:forEach items="${concept }" var="item">
                                	<tr>
                                        <td>${item.orderNo }</td>
                                        <td>${item.customerName }</td>
                                        <td id="taLeft"><c:out value="${item.device }" /></td>
                                        <td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.conceptFinishDate}" /></td>       
                                        <td>${item.quantity}</td>
                                        
                                        <td>${item.businessUserName }</td>
                                        <td>${item.designUserName }</td>
                                        <td>${item.customerUser }</td>
                                        <td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.orderDate}" /></td>       
                                        <td id="taLeft"><c:out value="${item.note }"></c:out></td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- //게시판 -->
