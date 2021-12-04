$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:'report_orderReport',
		columns:[[
		    {field:'name',title:'商品类型',width:100},
		    {field:'y',title:'销售额',width:100}
		]],
		singleSelect: true,
		onLoadSuccess:function(data){
			//alert(JSON.stringify(data));
			//显示图
			showChart(data.rows);
		}
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formdata = $('#searchForm').serializeJSON();
		if(formdata.endDate != ''){
			formdata.endDate += " 23:59:59";
		}
		$('#grid').datagrid('load',formdata);
	});
	
	
});

//显示图
function showChart(_data){
	$('#pieChart').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '销售统计'
        },
        //信用
        credits:{enabled:false},
        //导出
        exporting:{enabled:true},
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true
                },
                showInLegend: true
            }
        },
        series: [{
            name: "比例",
            colorByPoint: true,
            data: _data
        }]
    });
}