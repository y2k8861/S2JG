var map;
var markerInfo;
//출발지,도착지 마커
var marker_s, marker_e
var lon1, lat1;
var lon2, lat2;
//경로그림정보
var drawInfoArr = [];
var drawInfoArr2 = [];

var chktraffic = [];
var resultdrawArr = [];
var resultMarkerArr = [];
var searchOption = "10";
var trafficInfochk = "N";
initTmap();
function initTmap() {

    // 1. 지도 띄우기
//    map = new Tmapv2.Map("map_div", {
//        center : new Tmapv2.LatLng(37.56520450, 126.98702028),
//        width : "100%",
//        height : "400px",
//        zoom : 17,
//        zoomControl : true,
//        scrollwheel : true
//
//    });

    $("#btn_select").click(function() {
        // 2. API 사용요청
        var fullAddr1 =document.querySelector("#routeStartTerminal").value;
        var fullAddr2 =document.querySelector("#routeEndTerminal").value;
        var headers = {};
        headers["appKey"]="JRgtV5FJQ41HAHNM4hTiA7rGRIeQL6oJ15NhVii8";
        $.ajax({
            method : "GET",
            headers : headers,
            url : "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json&callback=result",
            async : false,
            data : {
                "coordType" : "WGS84GEO",
                "fullAddr" : fullAddr1+'터미널'
            },
            success : function(response) {

                var resultInfo = response.coordinateInfo; // .coordinate[0];
                console.log(resultInfo);

                // 검색 결과 정보가 없을 때 처리
                if (resultInfo.coordinate.length == 0) {
                    $("#result").text(
                    "요청 데이터가 올바르지 않습니다.");
                } else {
                    var resultCoordinate = resultInfo.coordinate[0];
                    if (resultCoordinate.lon.length > 0) {
                        // 구주소
                        lon1 = resultCoordinate.lon;
                        lat1 = resultCoordinate.lat;
                    } else {
                        // 신주소
                        lon1 = resultCoordinate.newLon;
                        lat1 = resultCoordinate.newLat
                    }
                    console.log("lon1 : " +lon1 + " , lat1 : " + lat1);
                }

                $.ajax({
                    method : "GET",
                    headers : headers,
                    url : "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json&callback=result",
                    async : false,
                    data : {
                        "coordType" : "WGS84GEO",
                        "fullAddr" : fullAddr2+'터미널'
                    },
                    success : function(response) {

                        var resultInfo = response.coordinateInfo; // .coordinate[0];
                        console.log(resultInfo);

                        // 3.마커 찍기
                        // 검색 결과 정보가 없을 때 처리
                        if (resultInfo.coordinate.length == 0) {
                            $("#result").text(
                            "요청 데이터가 올바르지 않습니다.");
                        } else {
                            var resultCoordinate = resultInfo.coordinate[0];
                            if (resultCoordinate.lon.length > 0) {
                                // 구주소
                                lon2 = resultCoordinate.lon;
                                lat2 = resultCoordinate.lat;
                            } else {
                                // 신주소
                                lon2 = resultCoordinate.newLon;
                                lat2 = resultCoordinate.newLat
                            }
                            console.log("lon2 : " +lon2 + " , lat2 : " + lat2);
                        }

                        // 경로 찾기
                        $.ajax({
                        type : "POST",
                        headers : headers,
                        url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result",
                        async : false,
                        data : {
                            "startX" : lon1,
                            "startY" : lat1,
                            "endX" : lon2,
                            "endY" : lat2,
                            "reqCoordType" : "WGS84GEO",
                            "resCoordType" : "EPSG3857",
                            "searchOption" : searchOption,
                            "trafficInfo" : trafficInfochk
                        },
                        success : function(response) {
                            console.log(response);
                            var resultData = response.features;
                            var routeDistance= resultData[0].properties.totalDistance
                            var tDistance = "총 거리 : "+ ( routeDistance / 1000).toFixed(1) + "km,";
                            var tTime = " 총 시간 : "+ (resultData[0].properties.totalTime / 60).toFixed(0) + "분,";

                            $("#result").text(tDistance + tTime);
                            document.querySelector("#routePrice").value = (( routeDistance / 10000).toFixed(1)*1000);
                            checkArray[2] = true;
                            console.log(checkArray);
                            let check = document.querySelector(".routePriceCheck");
                            msg = "통과";
                            checkArray[2] = true;
                            check.classList.remove('colorRed')
                            check.classList.add("colorGreen");
                            check.innerHTML = msg;
                        },
                        error : function(request, status, error) {
                            console.log("code:"
                                    + request.status + "\n"
                                    + "message:"
                                    + request.responseText
                                    + "\n" + "error:" + error);
                        }
                    });

                    },
                    error : function(request, status, error) {
                        console.log(request);
                        console.log("code:"+request.status + "\n message:" + request.responseText +"\n error:" + error);
                        // 에러가 발생하면 맵을 초기화함
                        // markerStartLayer.clearMarkers();
                        $("#result").html("");
                    }
                });
            },
            error : function(request, status, error) {
                console.log(request);
                console.log("code:"+request.status + "\n message:" + request.responseText +"\n error:" + error);
                // 에러가 발생하면 맵을 초기화함
                // markerStartLayer.clearMarkers();
                $("#result").html("");
            }
        });
    });
}