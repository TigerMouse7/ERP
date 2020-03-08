<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"	prefix="layout"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<layout:extends name="/WEB-INF/layout/DrawingMaster.jsp">

	<layout:put block="mainbody">
	
                <!-- 서브 타이틀 -->
                <div class="titArea">
                    <ul class="navi">
                        <li><span class="icon_home">홈</span></li>
                        <li>구매 LIST</li>
                        <li>구매 LIST 등록</li>
                    </ul>
                    <h2>구매 LIST 등록</h2>
                </div>
                <!-- 서브 타이틀 -->
                
                <!-- 서브 콘텐츠 -->
                <div class="contArea">
                
                	<!-- 테이블 타이틀 -->
                    <div class="searchArea">
                    	<div class="searchBox">
                        	<table>
                            	<caption> </caption>
                                <colgroup span="2">
                                <col style="width:12%;;" />
                                <col style="width:21%;" />
                                <col style="width:12%;" />
                                <col style="width:21%;" />
                                <col style="width:12%;" />
                                <col style="width:21%;" />
                                </colgroup>
                                <tr>
                                    <th>검색유형</th>
                                    <td>
                                        <label><input type="checkbox" name="aa" checked />전체</label>
                                        <label><input type="checkbox" name="aa" />업체</label>
                                    </td>
                                    <th>Order NO</th>
                                    <td>
                                        <input type="text" name="" value=" " class="w130"> - <input type="text" name="" value=" " class="w130">
                                    </td>
                                    <th>내용</th>
                                    <td>
                                        <input type="text" name="" value=" " class="w280">
                                    </td>
                                </tr>
                                <tr>
                                    <th>출도일</th>
                                    <td colspan="5"><input type="date" name="" value=" " class="w140"> - <input type="date" name="" value=" " class="w140"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="searchBtn"><a href="#" class="btn_blue">검색</a></div>
                    </div>
                    <!-- //테이블 타이틀 -->
                    
                    <!-- 테이블 콘텐츠 -->
                    <div class="listArea">
                    
                    	<!-- 타이틀 -->
                    	<div class="listTit">
                        	<h3>구매 LIST</h3>
                        </div>
                        <!-- //타이틀 -->
                        
                        <!-- 게시판 -->
                        <div class="listBox">
                        	<table>
                            	<caption> </caption>
                                <colgroup span="2">
                                <col style="width:7%;" />
                                <col style="width:15%;" />
                                <col style="width: ;" />
                                <col style="width:7%;" />
                                <col style="width:10%;" />
                                <col style="width:10%;" />
                                <col style="width:7%;" />
                                <col style="width:7%;" />
                                <col style="width:7%;" />
                                </colgroup>
                            	<thead>
                                	<tr>
                                        <th>Order <br/> No</th>
                                        <th>업체</th>
                                        <th>Device</th>
                                        <th>종류</th>
                                        <th>납품일</th>
                                        <th>영업담당</th>
                                        <th>설계담당</th>
                                        <th>출도일</th>
                                        <th>도면수량</th>
                                        <th>진행률</th>
                                    </tr>
                                </thead>
                                
                                <tbody>
                                	<tr>
                                    	<td>70001</td>
                                        <td>덕우전자</td>
                                        <td id="taLeft"><a href="javascript:PopWin('02_plan03_pop01.html','1000','700','no');">SI2692 PCB Bending System</a></td>
                                        <td>설비</td>
                                        <td>16.08.05</td>
                                        <td>박종선</td>
                                        <td>전진표</td>
                                        <td>16.08.05</td>
                                        <td>100</td>
                                        <td>%</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- //게시판 -->
                        
                    </div>
                    <!-- //테이블 콘텐츠 -->
                    
                </div>
                <!-- //서브 콘텐츠 -->
	</layout:put>
	
</layout:extends>