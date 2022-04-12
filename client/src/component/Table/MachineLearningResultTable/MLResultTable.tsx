import { Collapse, TableCell, TableRow } from '@mui/material';
import * as React from 'react'
import { useSelector } from 'react-redux';
import { RootState } from '../../../store';
import { downloadFile} from '../../../api/StorageAPI'

interface args{
    open : boolean,
    projectId : string,
    image_name: string
}

const MLResultTable : React.FC<args> = ({open,image_name})=>{
    const projectId=useSelector((state: RootState)=>state.ParticipantInfoReducer.participants.projectId)
    const [imageSrc,setImageSrc]=React.useState()
    useEffect(() => {
        downloadFile(projectId,image_name,
            (data : any)=>setImageSrc(data),
            (error)=>setImageSrc("error"))
      }, []);
    return (
        <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }}>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    {imageSrc !== null ? <img src={imageSrc} /> : "Loading..."}
                </Collapse>
            </TableCell>
        </TableRow>
    )
}
type Body = {
    [key : string] : string
}

type MetaData = {
    metadataId: string,
    projectId: string,
    body: Body
}

type VisualTableProps = {
    metaData: MetaData[]
};
let  metaData: MetaData[]=[{metadataId : "",projectId:"",body:{"123":"!23"}},
{metadataId : "",projectId:"",body:{"123":"!23"}}]
metaData.map(it=>it)
export default MLResultTable;

