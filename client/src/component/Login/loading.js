import * as React from 'react'
import { CircularProgress, Dialog, DialogContent, Stack, Typography} from '@mui/material'
export default function LoadingPage({message}){
    return(
        <Dialog open='true'>
            <DialogContent>
                <Stack alignItems="center">
                    <CircularProgress margin={2}/>
                    <Typography margin={2}>
                        {message}
                    </Typography>
                </Stack>
            </DialogContent>
        </Dialog>
    )
}