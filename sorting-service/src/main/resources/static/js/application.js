/**
 * 
 */
var randomNumberData;
$(document).ready(function () {
	
	$.ajax({
		url: "/previousValues",
		type: "GET",
		success: function(data){
			if(data != null){
				var prevObj = JSON.parse(data);
				$('#prevGenTd').text(prevObj.original);
				$('#prevSortedTd').text(prevObj.sorted);
			}
			
		}
	});
	
	$('#btnSort').hide();
	$('#generateRandom').click(function(e){
		console.log('button clicked');
		$.ajax({
			url: "getRandomNumbers",
			type: "GET"	,
			success : function(data){
				$('#divGeneratedNumbers').text(data);
				randomNumberData = data;
				$('#btnSort').show();
			}
		});
	});
	
	$('#btnSort').click(function(e){
		randomNumberData = JSON.parse(randomNumberData);
		$.ajax({
			url: "sortRandomNumbers",
			type: "POST",
			data:{randomObj: JSON.stringify(randomNumberData)},
			success : function(data){
				var sortedArray = JSON.parse(data);
				
				for(var sortedArrayCount =0 ; sortedArrayCount< sortedArray.length; sortedArrayCount++){
					var obj = sortedArray[sortedArrayCount];
					$('<div>').css({'word-wrap':'break-word'}).text('After Iteration').appendTo('.form');
					$('<div>').css({'word-wrap':'break-word'}).text(obj.value).appendTo('.form');
				}
			}
		});
	});
	
});