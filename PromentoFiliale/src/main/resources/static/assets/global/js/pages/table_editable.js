$(function () {

    function editableTable() {

    	var partners ;
    	

		
		
		$.ajax({
		    url: '/RestPartner/partners',
		    dataType: 'json',
		    success: function (data) {
//		        console.log("partners list succes"); 
//		        console.log(data); 

		        partners = data;
		        
		    },
	    error: function (msg) {
	        console.log(msg);        

	    },
	    async : false
		});
		
		
		var last_partner_id;
        function saveInBackend(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var partner_id = last_partner_id;
            var company_id = $("#company_id").val();
            
            console.log("last_partner_id" + last_partner_id);
            var mentor = {
            		"id" : (aData[5] == '' ? null : aData[5]),
            		"dateBegin" : aData[1],
            		"dateEnd" : aData[2],
            		"remuneration" : parseInt(aData[3])	
            };

            
            console.log("mentor : " + JSON.stringify(mentor));
    		$.ajax({
    		   	   headers: { 
    		   	        'Accept': 'application/json',
    		   	        'Content-Type': 'application/json' 
    		   	    },
    		    type: "POST",
    		    url: '/Mentoring/save/' + company_id + '/' + partner_id,
    		    dataType: 'json',
    		    data: JSON.stringify(mentor),
    		    success: function (data) {
    		        console.log("after saving mentor on backed : " + data); 
    	            oTable.fnUpdate(data.id, nRow, 5, false);

    		        
    		    },
    	    error: function (msg) {
    	        console.log(msg);        

    	    },
    	    async : false
    		});
            

        }

    	
        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }
            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            
//            jqTds[0].innerHTML = '<input type="text" class="form-control small" value="' + aData[0] + '">';
            console.log("partners " + partners);
            var options ;
            
				
			
            for (let p of partners) {
            	
//            	console.log("p" + p);
                options += '<option value="' + p.id + '"  ' + (aData[0] == p.lastName ? "selected" : "") +  '>' + p.lastName + '</option>' ;

			}
            
            jqTds[0].innerHTML = '<select id="partners" name="partners">' +  options + '</select>';

  
            

            jqTds[1].innerHTML = '<input type="Date" class="form-control small" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="Date" class="form-control small" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="number" min="0" max="100" class="form-control small" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<div class="text-right"><a class="edit btn btn-sm btn-success" href="">Enregister</a> <a class="delete btn btn-sm btn-danger" href=""><i class="icons-office-52"></i></a></div>';
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            last_partner_id = $("#partners option:selected").val();
            oTable.fnUpdate($("#partners option:selected").text(), nRow, 0, false);
            oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 3, false);
            oTable.fnUpdate('<div class="text-right"><a class="edit btn btn-sm btn-default" href=""><i class="icon-note"></i></a> <a class="delete btn btn-sm btn-danger" href=""><i class="icons-office-52"></i></a></div>', nRow, 4, false);
            oTable.fnDraw();
        }

        function cancelEditRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate($("#partners option:selected").text(), nRow, 0, false);
            oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 3, false);
            oTable.fnUpdate('<a class="edit btn btn-sm btn-default" href=""><i class="icon-note"></i></a>', nRow, 4, false);
            oTable.fnDraw();
        }

        var oTable = $('#table-editable').dataTable({
        	
            "aLengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],

            // set the initial value
            "iDisplayLength": 10,
            "bPaginate": false,
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page",
                "oPaginate": {
                    "sPrevious": "Prev",
                    "sNext": "Next"
                },
                "sSearch": "" 
            },
            "aoColumnDefs": [
                {
                    "targets": [ 5 ],
                    "visible": false,
                    "searchable": false
                }
            ],

            
        
        });

        jQuery('#table-edit_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
        jQuery('#table-edit_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

        var nEditing = null;

        $('#table-edit_new').click(function (e) {
            e.preventDefault();
            var aiNew = oTable.fnAddData(['', '', '', '',
                    '<p class="text-center"><a class="edit btn btn-dark" href=""><i class="fa fa-pencil-square-o"></i>Editer</a> <a class="delete btn btn-danger" href=""><i class="fa fa-times-circle"></i> Supprimer</a></p>'
            ,'']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
        });

        $('#table-editable a.delete').live('click', function (e) {
            e.preventDefault();
            if (confirm("Are you sure to delete this row ?") == false) {
                return;
            }
            var nRow = $(this).parents('tr')[0];
//            console.log(nRow);
            oTable.fnDeleteRow(nRow);
            // alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            var aData = oTable.fnGetData(nRow);
            var id  = aData[5];

    		$.ajax({
    		    url: '/Mentoring/delete/' + id,
    		    dataType: 'json',
    		    success: function (data) {        
    		    },
    	    error: function (msg) {
    	        console.log(msg);        

    	    }
    		});
            
            
            
            
        });

        $('#table-editable a.cancel').live('click', function (e) {
            e.preventDefault();
            if ($(this).attr("data-mode") == "new") {
                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }
        });

        $('#table-editable a.edit').live('click', function (e) {
            e.preventDefault();
            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Enregister") {
                 /* This row is being edited and should be saved */
                saveRow(oTable, nEditing);
                nEditing = null;
                console.log("row saved : "  + oTable.fnGetData(nRow));
                // alert("Updated! Do not forget to do some ajax to sync with backend :)");
                saveInBackend(oTable, nRow);
            } else {
                 /* No row currently being edited */
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        });

        $('.dataTables_filter input').attr("placeholder", "Rechercher...");

        
        
		$.ajax({
		    url: '/Mentoring/mentors/' + $("#company_id").val(),
		    dataType: 'json',
		    success: function (data) {
//		        console.log("partners list succes"); 
//		        console.log("sucess"  + JSON.stringify(data)); 
//		        console.log(JSON.stringify(data));
		        for (let mentor of data) {
			        oTable.fnAddData( [
			        	mentor.mentor.lastName,
			        	mentor.dateBegin,
			        	mentor.dateEnd,
			        	mentor.remuneration,
			        	'<div class="text-right"><a class="edit btn btn-sm btn-default" href=""><i class="icon-note"></i></a> <a class="delete btn btn-sm btn-danger" href=""><i class="icons-office-52"></i></a></div>'	,
						mentor.id

			        ] );
				}
	

		        
		        
		    },
	    error: function (msg) {
	        console.log(msg);        

	    }
	    
		});
		
		
    };

    editableTable();

});