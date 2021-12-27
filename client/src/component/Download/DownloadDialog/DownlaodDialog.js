import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent, Stack } from "@mui/material";
import { Dialog } from '@mui/material';
import { DialogTitle } from '@mui/material';

export default function DownloadDialog(){
    return(
        <Dialog>
            <DialogTitle>파일 업로드</DialogTitle>
            {/* 업로드된 Dicom 파일 표시 */}
            <Stack></Stack>
            {/* 업로드된 csv 파일 상태 표시 */}
            <Stack></Stack>
            {/* 경고창 표시 */}
            <div></div>
            <div>
                <Button>확인</Button>
                <Button>취소</Button>
            </div>
        </Dialog>
    )
}