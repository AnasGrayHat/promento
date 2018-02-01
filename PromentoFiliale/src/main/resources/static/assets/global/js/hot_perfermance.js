
	
	
			$.ajax({
			    url: '/indicator/perfermance/' + $("#company_id").val() + '/2018',
			    dataType: 'json',
			    success: function (data) {
			        console.log("succes"); 

			        console.log(data);        
			        handstonable_perfermance.loadData(data);
			        
			        
			    },
		    error: function (msg) {
		        console.log(msg);        

		    }
			});
			
			
	//janvier	février	mars	avril	mai	juin
	//juillet	août	septembre	octobre	novembre	décembre
	var handstonable_perfermance = Handsontable(document.getElementById('handstonable_perfermance'), {
	  height: 150,
	  colWidths: 75,
	  minCols: 13,
	  minRows: 4,
	  rowHeaders: false,
	  colHeaders: [
	    'janvier',
	    'février',
	    'mars',
	    'avril',
	    'mai',
	    'juin',
	    'juillet',
	    'août',
	    'septembre',
	    'octobre',
	    'novembre',
	    'décembre',
	    'Cumul',
	  ],  
	  
	  columns: [
	      {data: 'january.value' },
	      {data: 'february.value'},
	      {data: 'march.value'},
	      {data: 'april.value'},
	      {data: 'may.value'},
	      {data: 'june.value'},
	      {data: 'july.value'},
	      {data: 'august.value'},
	      {data: 'september.value'},
	      {data: 'october.value'},
	      {data: 'november.value'},
	      {data: 'december.value'},
	      {data: ''},
	    ] ,
	    
	    manualColumnResize: true,

	    rowHeaderWidth: 70,

	    rowHeaders: [
	    'CA',
	    'Charges',
	    'Trésorie',
	    'Résultat',

	  ],
	  columnSorting: false,
	  filters: true,
	  dropdownMenu: false,
	  contextMenu: false,
	  autoRowSize: false,
	  manualColumnMove: false,
	  manualRowMove: false,
	  fillHandle: {
	    autoInsertRow: false,
	  }


	});


	handstonable_perfermance.addHook('afterChange', function(changes, src) {
		  

			
			if(changes)
			if(changes[0][0] == 0 && changes[0][1] == "")
			return;
			
			if(changes)
			if(changes[0][0] == 1 && changes[0][1] == "")
				return;
			
			if(changes)
			if(changes[0][0] == 3)
				return;
			
			
			
			for (var i = 0; i < 12; i++) {
				var ca = handstonable_perfermance.getDataAtCell(0, i );
				var charges = handstonable_perfermance.getDataAtCell(1, i );
				
				  if(ca != null && ca!= ""  && charges != null &&  charges !="")
				handstonable_perfermance.setDataAtCell(3, i , ca - charges);

			}
			
	  	for (var i = 0; i < 4; i++) {
	  		
	  		if(i == 2) continue;
	  		
		  	var cumul = 0;
	  		var j;
		  	for (j = 0; j < 12; j++) {
		  		var value = handstonable_perfermance.getDataAtCell(i, j );
		  		
		  		if(value != null && value != "")
		  		cumul +=  parseFloat(value);
		  		
			}
		  	
		  	handstonable_perfermance.setDataAtCell( i , j , cumul);
		}
	  	
	  	updateLatestEstimate();
	  	updateChart(handstonable_perfermance.getSourceData()[3] , 1);


});



