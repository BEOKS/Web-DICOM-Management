export class CsvFileHandler {
    constructor(csvFile) {
        this.csvFile=csvFile;
        this.loadCsv(csvFile); 
        console.log('CsvFileHandler: csv',this.csvText);
        this.patientIDLabel='Patient_ID'
    }

    async loadCsv(file) {
        if(file instanceof File){
            let result = await new Promise((resolve) => {
                let fileReader = new FileReader();
                fileReader.onload = (e) => resolve(fileReader.result);
                fileReader.readAsText(file);
            });
            console.log('loadCsv: csv',this.csvJSON(result),typeof(result));
            this.csvText=result
        }
        return null;
    }
    csvJSON(csv){
        console.log('csvJson',csv,typeof(csv))
        var lines=csv.split("\n");
      
        var result = [];

        var headers=lines[0].split(",");
      
        for(var i=1;i<lines.length;i++){
      
            var obj = {};
            var currentline=lines[i].split(",");
      
            for(var j=0;j<headers.length;j++){
                obj[headers[j]] = currentline[j];
            }
      
            result.push(obj);
      
        }
      
        //return result; //JavaScript object
        return JSON.stringify(result); //JSON
      }
    getContentOfColumn(columnName){
        
    }
    getPatientIDList(){

    }
}
