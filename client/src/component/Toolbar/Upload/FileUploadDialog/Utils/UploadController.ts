import axios, { Axios, AxiosResponse } from "axios";
import fastq from "fastq";
interface Args{
    params : any
    index : number
}
/**
 * 대용량의 데이터를 하나의 POST에 담아 전송하지 않고 
 * 여러개의 API로 나누어서 전송하기 위한 함수이다.
 * POST 요청과 데이터를 입력받아
 * 동시에 concurrency 만큼의 API Request를 생성한다.
 * 각 API Request의 사이즈는 chunkSize의 크기만큼 설정된다.
 * @param data 
 * @param request : post 요청을 위한 axios post 인스턴스, chunk 데이터를 입력받아 수행할 Axios request를 입력한다.
 * @param callbackSuccess : 각 request의 번호를 인자로 받는 콜백 성공 리스너이다. (번호는 0번부터 시작한다.)
 * @param callbackError : 각 request의 번호를 인자로 받는 콜백 에러 리스너이다.
 * @param concurrency 
 * @param chunkSize 
 */
export async function concurrencyPOSTHandler(data : any[],
    postRequest : (chunkData : any[]) =>Promise<AxiosResponse<any, any>>,
    callbackSuccess: (completeAPICount :number,apiNumber : number)=>void,
    callbackError: (apiNumber:number,error: any)=>void,
    concurrency=10,
    chunkSize=3000
    ){
    
    const apiNumber= Math.ceil(data.length/chunkSize)
    const workerFunction=async (args:Args)=>{
      postRequest(args.params).then(response=>{
        callbackSuccess(args.index+1,apiNumber)
      }).catch(error=>{
        callbackError(args.index,error)
      })
    }

    const consecutiveQueue = fastq.promise(
      workerFunction,
      concurrency
    )
    
    const req = [];
    for (let i = 0; i < data.length; i+=chunkSize) {
      const params = data.slice(i,i+chunkSize);
      req.push(consecutiveQueue.push({ params, index: i/chunkSize }));
    }
    Promise.all(req);

}
// export async function consecutiveRequest(data: any[], 
//   requester: (data :any)=>void, concurrency = 10) {
//   const workerFunction= async (args : Args) => {
//     try {
//         requester(args.params);
//     } catch (error) {
//         console.error(error);
//         return null;
//     }
//   };

//   const consecutiveQueue = fastq.promise(
//     workerFunction, // fastq take a worker function 
//     concurrency // number of requests only allowed at a time
//   );

//   const req = [];
//   for (let i = 0; i < data.length; i++) {
//     const params = data[i];
//     req.push(consecutiveQueue.push({ params, index: i }));
//   }


// }