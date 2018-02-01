


	function getData() {
		  return [
		    ]
		    
		};
				
	var data = getData();
	var rowsCount = data.length;
	//janvier	février	mars	avril	mai	juin
	//juillet	août	septembre	octobre	novembre	décembre
	var handstonable_latest_estimate = Handsontable(document.getElementById('handstonable_latest_estimate'), {
	  data: data,
	  height: 150,
	  colWidths: 75,
	  minCols: 13,
	  minRows: 3,
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
	  ], columns: [

	      {data: 'january.value' ,readOnly: true},
	      {data: 'february.value',readOnly: true},
	      {data: 'march.value',readOnly: true},
	      {data: 'april.value',readOnly: true},
	      {data: 'may.value',readOnly: true},
	      {data: 'june.value',readOnly: true},
	      {data: 'july.value',readOnly: true},
	      {data: 'august.value',readOnly: true},
	      {data: 'september.value',readOnly: true},
	      {data: 'october.value',readOnly: true},
	      {data: 'november.value',readOnly: true},
	      {data: 'december.value',readOnly: true},
	      {data: ''},

	    ],
	    manualColumnResize: true,

	    rowHeaderWidth: 70,

	    rowHeaders: [
	    'CA',
	    'Charges',
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
	
	

	

	function updateLatestEstimate()
	{
		
		
		for (var i = 0; i < 12; i++) {
			var caEstimated 	 = handstonable_budget.getDataAtCell(0, i );
			var chargesEstimated = handstonable_budget.getDataAtCell(1, i );
			
		
			
			
			handstonable_latest_estimate.setDataAtCell( 0, i , caEstimated);
			handstonable_latest_estimate.setDataAtCell( 1, i , chargesEstimated);


		}
		for (var i = 0; i < 12; i++) {
			var caReal			 = handstonable_perfermance.getDataAtCell(0, i );
			var chargesReal      = handstonable_perfermance.getDataAtCell(1, i );
			
		
			
			  if(caReal != null && caReal != ""  && chargesReal != null &&  chargesReal != "")
			  	{
					handstonable_latest_estimate.setDataAtCell( 0, i , caReal);
					handstonable_latest_estimate.setDataAtCell( 1, i , chargesReal);		  

			  	}
		


		}
		
		
		for (var i = 0; i < 12; i++) {
			var ca = 	  handstonable_latest_estimate.getDataAtCell(0, i );
			var charges = handstonable_latest_estimate.getDataAtCell(1, i );
			
		  if(ca != null && ca!= ""  && charges != null &&  charges !="")
			  handstonable_latest_estimate.setDataAtCell( 2, i , ca - charges);

		}
		
  	for (var i = 0; i < 3; i++) {
	  	var cumul = 0;
  		var j;
	  	for (j = 0; j < 12; j++) {
	  		
	  		var value = handstonable_latest_estimate.getDataAtCell(i, j );
	  		
	  		if(value != null && value != "")
	  		cumul +=  parseFloat(value);
	  		
		}
	  	
	  	handstonable_latest_estimate.setDataAtCell( i , j , cumul);
	}

		
	}
	
