import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import axios from "axios"
import { useState, useEffect } from 'react';

const metaDataURI="http://localhost:8000/api/metadata"


export default function DataGridDemo() {
    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const columns=[
        {field: 'anonymized_id',width:150},
        {field: 'modality',width:150},
        {field: 'age',width:150},
        {field: 'manufacturer',width:200},
        {field: 'manufacturerModelName',width:250},
        {field: 'type',width:150},
    ]
    useEffect(() => {
        const fetchUsers = async () => {
          try {
            // 요청이 시작 할 때에는 error 와 users 를 초기화하고
            setError(null);
            setMetadata(null);
            // loading 상태를 true 로 바꿉니다.
            setLoading(true);
            const response = await axios.get(metaDataURI);
            console.log(response.data)
            setMetadata(response.data); // 데이터는 response.data 안에 들어있습니다.
          } catch (e) {
            setError(e);
          }
          setLoading(false);
        };
        fetchUsers();
      }, []);
    
    if (loading) return <div>로딩중..</div>;
    if (error) return <div>{error}</div>;
    if (!metadata) return null;
    return (
        <div style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height:900
        }}>
            <div style={{
                width:'60%',
                height:800,
                color:"white"
            }}>
            <DataGrid
                getRowId={(row)=>row.anonymized_id}
                rows={metadata}
                columns={columns}
                checkboxSelection
                disableSelectionOnClick
                
            />
            </div>
        </div>
    );
}