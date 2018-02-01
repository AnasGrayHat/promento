



			$.ajax({
			    url: '/indicator/budget/' + $("#company_id").val() + '/2018',
			    dataType: 'json',
			    success: function (data) {
			        console.log("succes"); 

			        handstonable_budget.loadData(data);
			        
			        
			    },
		    error: function (msg) {
		        console.log(msg);        

		    }
			});
			
		
			

			
			
//	var data = getData();
	//janvier	février	mars	avril	mai	juin
	//juillet	août	septembre	octobre	novembre	décembre
	var handstonable_budget = Handsontable(document.getElementById('handstonable_budget'), {
//	  data: data,
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
	  ],   	  columns: [

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


	
	var $$ = function(id) {
      return document.getElementById(id);
    }, load = $$('load'),save = $$('save');


  Handsontable.dom.addEvent(save, 'click', function() {
    // save all cell's data
	  
	  //there is 2 indicators that i should send CA and charges , result no
	  var newDataBudget = handstonable_budget.getSourceData().slice(0,-1);
	  var newDataPerfermance = handstonable_perfermance.getSourceData().slice(0,-1);

	  
	  delete newDataBudget[0][""];
	  delete newDataBudget[1][""];
	  
	  delete newDataPerfermance[0][""];
	  delete newDataPerfermance[1][""];
	  delete newDataPerfermance[2][""];

	  
	  

//	  console.log(handstonable_budget.getSourceData());

      $.ajax({
	    	   headers: { 
	    	        'Accept': 'application/json',
	    	        'Content-Type': 'application/json' 
	    	    },
	        type: "POST",
	        url: "/indicator/save",
	        data: JSON.stringify(newDataBudget),
	        success: function(result) {
	        	
	            toastr.success("Enregistrement avec succés");

	        },
	        error: function(){
	            toastr.error("erreur lors de l'Enregistrement");
	          }
	    	    
	    	    
	    });
	    

      $.ajax({
   	   headers: { 
   	        'Accept': 'application/json',
   	        'Content-Type': 'application/json' 
   	    },
       type: "POST",
       url: "/indicator/save",
       data: JSON.stringify(newDataPerfermance),

       
   });
      



    });
  
  
  handstonable_budget.addHook('afterChange', function(changes, src) {
	  

			
			
			if(changes)
			if(changes[0][0] == 0 && changes[0][1] == "")
			return;
			
			if(changes)
			if(changes[0][0] == 1 && changes[0][1] == "")
				return;
			
			if(changes)
			if(changes[0][0] == 2)
				return;
			
			for (var i = 0; i < 12; i++) {
				var ca = 	  handstonable_budget.getDataAtCell(0, i );
				var charges = handstonable_budget.getDataAtCell(1, i );
				
			  if(ca != null && ca!= ""  && charges != null &&  charges !="")
			  handstonable_budget.setDataAtCell( 2, i , ca - charges);

			}
			
	  	for (var i = 0; i < 3; i++) {
		  	var cumul = 0;
	  		var j;
		  	for (j = 0; j < 12; j++) {
		  		
		  		var value = handstonable_budget.getDataAtCell(i, j );
		  		
		  		if(value != null && value != "")
		  		cumul +=  parseFloat(value);
		  		
			}
		  	
		  	handstonable_budget.setDataAtCell( i , j , cumul);
		}
	  	
	  	updateLatestEstimate();
	  	updateChart(handstonable_budget.getSourceData()[2] , 0);
	  	
	  		
		
	  	

  });
  
  
  
