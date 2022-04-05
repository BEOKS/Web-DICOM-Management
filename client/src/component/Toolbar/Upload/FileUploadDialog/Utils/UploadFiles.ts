/**
 *
 * @param csvFile
 * @param imageFiles
 * @return 업로드를 성공하면 true를 반환합니다.
 */
export default function uploadFiles(csvFile: File | undefined,imageFiles : File[]): boolean{
    if (csvFile===undefined){
        console.error("csv file is undefined")
        return false
    }

    return true
}