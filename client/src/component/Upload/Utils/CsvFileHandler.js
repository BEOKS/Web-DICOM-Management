export class CsvFileHandler {
    constructor(csvFile) {
        this.csvFile=csvFile;
    }
    updateFile(csvFile){
        this.csvFile=csvFile;
    }
    /**
     *  this.csvJson에 로드한 csv 정보를 json 형태로 저장
     * @param {*} file 
     * @returns 
     */
    async loadCsv(onloadCallBack) {
        if(this.csvFile instanceof File){
            let result = await new Promise((resolve) => {
                let fileReader = new FileReader();
                fileReader.onload = (e) => resolve(fileReader.result);
                fileReader.readAsText(this.csvFile);
            });
            this.csvJson=this.csvJSON(result) 
            onloadCallBack(this.csvJson)
        }
    }
    csvJSON(csv){
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
        return result; //JSON
      }
    getContentOfColumn(columnName){
        
    }
    getPatientIDList(){
        return this.csvJson.map( json => json.anonymized_id)
    }
    uploadFile(){
        
    }
}
