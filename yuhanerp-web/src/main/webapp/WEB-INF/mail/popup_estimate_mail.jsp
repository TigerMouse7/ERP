<!DOCTYPE html>
<html lang="ko-KR">
<head>
	<meta charset="utf-8">
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">

	<link rel="stylesheet" type="text/css" href="/css/style_pop.css">

	<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/menu.js"></script>
	<script type="text/javascript" src="/js/auto_datepicker.js"></script>

    <!-- 멀티셀렉트 -->
    <link rel="stylesheet" type="text/css" href="/css/multi.css">
	<!-- //멀티셀렉트 -->
    <!-- 트리메뉴 -->
    <script type="text/javascript" src="/js/checktree.js"></script>
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
        	<h1>견적의뢰 메일 발송</h1>
        </div>
        <!-- //팝업 타이틀 -->

        <!-- 팝업 콘텐츠 -->
        <div class="popCont">

            <!-- 팝업 서브타이틀 -->
            <div class="pcTit">
                <h2>메일 발송 리스트 – H테크</h2>
            </div>
            <!-- //팝업 서브타이틀 -->

            <!-- 회원찾기 -->
            <div class="client">

                <div class="client_search">
                	<!-- 부서별 검색일경우 -->
                	<ul>
                        <li>
                        	<span>거래처</span>
                            <em><input type="text" value="H테크" id="w180" readonly="readonly"></em>
                        </li>
                        <li><span>담당자</span> <em><input type="text" value=" " id="w170" readonly="readonly"></em></li>
                        <li><span>담당자 메일</span><em class="pl3"><input type="text" value=" "  id="w320" readonly="readonly"></li>
                    </ul>
                    <!-- //부서별 검색일경우 -->

                </div>

            </div>
            <!-- //회원찾기 -->

            <!-- 팝업 서브타이틀 -->
            <div class="pcTit">
                <h2>메일 발송 내용</h2>
            </div>
            <!-- //팝업 서브타이틀 -->

            <!-- 메일발송 내역 -->
            <div class="mail_con">

                <!-- 테이블 콘텐츠 -->
                <div class="popList">

                	<div class="popListTit">
                    	<h4>견적서 요청 작성(2016-10-28)</h4>
                        <h3>Device : SI2692 PCB Bending</h3>
                        <h4>Order No : [50001]</h3>
                    </div>

                    <!-- 게시판 -->
                    <div class="popBoard pb20">
                        <table>
                            <caption> </caption>
                            <colgroup span="2">
                            <col style="width:9%;" />
                            <col style="width:10%;" />
                            <col style="width:10%;" />
                            <col style="width:9%;" />
                            <col style="width:9%;" />
                            <col style="width:6%;" />
                            <col style="width:6%;" />
                            <col style="width:9%;" />
                            <col style="width:9%;" />
                            <col style="width:7%;" />
                            <col style="width:15%;" />
                            </colgroup>
                            <tr>
                                <th>도면번호</th>
                                <th>Description</th>
                                <th>Dimensions</th>
                                <th>Mat’l</th>
                                <th>열처리</th>
                                <th>수량</th>
                                <th>Spare</th>
                                <th>후처리</th>
                                <th>납기일</th>
                                <th>도면</th>
                                <th>비고</th>
                            </tr>
                            <tr>
                                <td>50001-3001</td>
                                <td>Vacuum Tip</td>
                                <td>&nbsp;</td>
                                <td>A6061</td>
                                <td>&nbsp;</td>
                                <td>10</td>
                                <td>2</td>
                                <td>Nickel</td>
                                <td>17.09.11</td>
                                <td><a href="#" class="btn_file">파일</a></td>
                                <td>후처리 포함</td>
                            </tr>
                             <tr>
                                <td>50001-3001</td>
                                <td>Vacuum Tip</td>
                                <td>&nbsp;</td>
                                <td>A6061</td>
                                <td>&nbsp;</td>
                                <td>10</td>
                                <td>2</td>
                                <td>Nickel</td>
                                <td>17.09.11</td>
                                <td><a href="#" class="btn_file">파일</a></td>
                                <td>후처리 포함</td>
                            </tr>
                             <tr>
                                <td>50001-3001</td>
                                <td>Vacuum Tip</td>
                                <td>&nbsp;</td>
                                <td>A6061</td>
                                <td>&nbsp;</td>
                                <td>10</td>
                                <td>2</td>
                                <td>Nickel</td>
                                <td>17.09.11</td>
                                <td><a href="#" class="btn_file">파일</a></td>
                                <td>후처리 포함</td>
                            </tr>
                             <tr>
                                <td>50001-3001</td>
                                <td>Vacuum Tip</td>
                                <td>&nbsp;</td>
                                <td>A6061</td>
                                <td>&nbsp;</td>
                                <td>10</td>
                                <td>2</td>
                                <td>Nickel</td>
                                <td>17.09.11</td>
                                <td><a href="#" class="btn_file">파일</a></td>
                                <td>후처리 포함</td>
                            </tr>
                        </table>
                    </div>
                    <!-- //게시판 -->

                </div>
                <!-- 테이블 콘텐츠 -->

                <!-- 메일본문내용 -->
                <div class="mail_text">
                	<textarea rows="10" placeholder="추가로 전달할 내용을 기재해 주세요"></textarea>
                </div>
                <!-- //메일본문내용 -->

                <!-- 주의사항 -->
                <div class="care_text">
                	<h6>※ 매입업체 요청사항 ※</h6>
                	<ol>
                    	<li>1. 세금계산서, 거래명세서 발행시, <span>ORDER NO.</span> 필히 기재.</li>
                        <li>2. 납품시, 거래명세서 필히 공급. (계산서 내역 검토시, 거래명세서 확인)</li>
                    </ol>
                    <p><span>상기 사항 미 적용시, 결재 승인 불가합니다.</</p>
                </div>
                <!-- /주의사항 -->

            </div>
            <!-- //메일발송 내역 -->

            <!-- 팝업 버튼 -->
            <div class="popBtn">
                <a href="#" class="btn_gray">취소</a>
                <a href="#" class="btn_blue">메일 발송</a>
            </div>
            <!-- //팝업 버튼 -->

        </div>
        <!-- //팝업 콘텐츠 -->






	</div>

<!-- 멀티셀렉트 -->
<script src='/js/multiselect03.js'></script>
<script src='/js/multiselect02.js'></script>
<script src="/js/multiselect01.js"></script>
<!-- //멀티셀렉트 -->
</div>
</body>
</html>
