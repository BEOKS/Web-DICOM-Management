import * as React from 'react';
import Box from "@mui/material/Box";
import UploadButton from "../Upload/UploadButton";

export default function UpDownloadToolbar() {
    return (
        <Box
            sx={{
                width: '100%',
                minWidth: 750,
                mt: 3,
                px: 3
            }}
        >
            <Box
                sx={{
                    width: '100%',
                    border: 'solid 1px #cfcfcf',
                    borderRadius: '4px',
                    px: 3,
                    py: 1,
                }}
            >
                <UploadButton />
            </Box>
        </Box>
    );
}