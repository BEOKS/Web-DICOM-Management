import * as React from "react";
import Toolbar from "@mui/material/Toolbar";
import { alpha } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import FilterListIcon from "@mui/icons-material/FilterList";
import PropTypes from "prop-types";
import DeleteRowDialog from "./DeleteRowDialog";
import DeleteNonReferenceDialog from "./DeleteNonReferenceDialog"
import { useState } from "react";
import { Fragment } from "react";
import { Stack } from "@mui/material";
import exportCSVFile from "./Utils/exportCSVFile";
import axios from 'axios'
const hostLocation = process.env.SERVER_HOST ? process.env.SERVER_HOST : 'localhost'

export default function EnhancedTableToolbar(props) {
    const { numSelected, selected, metaDataUpdated, setMetaDataUpdated, selectedStudyUIDList, selectedPatientIDList, isNonReferenced,metadata,project } = props;
    const [open, setOpen] = useState(false);

    const handleDicomDownloadButtonClick = () => {
        //download files
        alert('현재 이미지 파일 시스템 개발을 위해 일시적으로 Dicom 서비스를 중단하였습니다. 추후 복구 예정입니다.')
        // const selectedMetadataList=metadata.filter(it => selected.includes(it.metadataId)).map(it=>it.body)
        // selectedMetadataList.forEach(selectedMetadataList => {
        //     const studyUID=selectedMetadataList.StudyInstanceUID
        //     axios.get(`http://${hostLocation}:8080/api/study/${studyUID}/dicom`,{responseType : 'blob'})
        //         .then((response) => {
        //             const url = window.URL.createObjectURL(new Blob([response.data]));
        //             const link = document.createElement('a');
        //             link.href = url;
        //             link.setAttribute('download', `${studyUID}.zip`); //or any other extension
        //             document.body.appendChild(link);
        //             link.click();
        //         });

        // });
    };
    const handleCSVDownloadButtonClick=()=>{
        const selectedMetadataList=metadata.filter(it => selected.includes(it.metadataId)).map(it=>it.body)
        console.log('handleCSVDownloadButtonClick',metadata,selectedMetadataList)
        exportCSVFile(selectedMetadataList,project.projectName+'.csv');
    };

    const handleDeleteDialogOpen = () => {
        setOpen(true);
    };

    const handleDeleteDialogClose = () => {
        setOpen(false);
    };

    return (
        <Toolbar
            sx={{
                pl: { sm: 2 },
                pr: { xs: 1, sm: 1 },
                ...(numSelected > 0 && {
                    bgcolor: (theme) =>
                        alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
                }),
            }}
        >
            {numSelected > 0 ? (
                <Typography
                    sx={{ flex: '1 1 100%' }}
                    color="inherit"
                    variant="subtitle1"
                    component="div"
                >
                    {numSelected} selected
                </Typography>
            ) : (
                <Typography
                    sx={{ flex: '1 1 100%' }}
                    variant="h6"
                    id="tableTitle"
                    component="div"
                >
                    Dicom List
                </Typography>
            )}

            {numSelected > 0 ? (
                <Stack direction="row">
                    <Fragment>
                        <Tooltip title="Delete">
                            <IconButton onClick={handleDeleteDialogOpen}>
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                        {isNonReferenced
                        ? (
                            <DeleteNonReferenceDialog
                                open={open}
                                onClose={handleDeleteDialogClose}
                                selectedPatientIDList={selectedPatientIDList}
                                metaDataUpdated={metaDataUpdated}
                                setMetaDataUpdated={setMetaDataUpdated}
                            />
                        )
                        : (
                            <DeleteRowDialog
                                open={open}
                                onClose={handleDeleteDialogClose}
                                selected={selected}
                                selectedStudyUIDList={selectedStudyUIDList}
                                metaDataUpdated={metaDataUpdated}
                                setMetaDataUpdated={setMetaDataUpdated}
                                project = {project}
                        />
                        )}
                    </Fragment>
                    <Tooltip title="Download CSV">
                        <IconButton onClick={() => handleCSVDownloadButtonClick()}>
                            <FileDownloadIcon />
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="Download Dicom">
                        <IconButton onClick={() => handleDicomDownloadButtonClick()}>
                            <CloudDownloadIcon />
                        </IconButton>
                    </Tooltip>
                </Stack>
            ) : (
                <Tooltip title="Filter list" style={{display: "none"}}>
                    {/* 필터 기능 추가 시 Tooltip의 style prop 삭제하기 */}
                    <IconButton>
                        <FilterListIcon />
                    </IconButton>
                </Tooltip>
            )}
        </Toolbar>
    );
}

EnhancedTableToolbar.propTypes = {
    numSelected: PropTypes.number.isRequired,
};