

var ctx = document.getElementById("financial_chart").getContext('2d');
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [
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
    	  ],
        datasets: [{
            label: 'Résultat Prévisionnel',
            backgroundColor             : 'rgba(151,187,205,0.2)',
            strokeColor           : 'rgba(151,187,205,1)',
            borderColor            : 'rgba(151,187,205,1)',
            pointStrokeColor      : '#fff',
            pointHighlightFill    : '#fff',
            pointHighlightStroke  : 'rgba(151,187,205,1)',
            fill: true

        },
        {
            label: 'Résultat Réel',
            backgroundColor: 'rgba(220,220,220,0.2)',
            strokeColor: 'rgba(220,220,220,1)',
            borderColor : 'rgba(220,220,220,1)',
            pointStrokeColor: '#fff',
            pointHighlightFill: '#fff',
            pointHighlightStroke: 'rgba(220,220,220,1)',
            fill: true

        }
        
        
        ]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:'Résultat Financier'
        },
        
        
        
        scales: {
        	
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Mois'
                }
            }],
        	
        	
            yAxes: [{
                ticks: {
                    beginAtZero:true,

                },
            display: true,
            scaleLabel: {
                display: true,
                labelString: 'Résultat'
            },

            }]
        }
    }
});



function updateChart(dataSource , dataSet)
{

  	var i = 0;
  	for ( var key in dataSource) {
  		
//  		console.log(key + " , " + dataSource[key].value);

  		if(key != "" && key != null)
  			{
  			
  			var value = 	dataSource[key].value;
  			if(value == "") value = null;
  			
  			
  	  		myChart.data.datasets[dataSet].data[i++] = value;

  			}
  		
	}

  	console.log(myChart.data.datasets[dataSet].data);
//	
// 
	myChart.update();

}


