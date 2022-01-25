import * as React from "react";
import Toolbar from "@mui/material/Toolbar";
import { alpha } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FilterListIcon from "@mui/icons-material/FilterList";
import PropTypes from "prop-types";
import DeleteRowDialog from "./DeleteRowDialog";
import DeleteNonReferenceDialog from "./DeleteNonReferenceDialog"
import { useState } from "react";
import { Fragment } from "react";
import { Stack } from "@mui/material";

const hostLocation = process.env.SERVER_HOST ? process.env.SERVER_HOST : 'localhost'

export default function EnhancedTableToolbar(props) {
    const { numSelected, selected, metaDataUpdated, setMetaDataUpdated, selectedPatientIDList, isNonReferenced } = props;
    const [open, setOpen] = useState(false);

    const handleDownloadButtonClick = (selectedPatientID) => {
        //download files
        selectedPatientIDList.forEach(element => {
            window.location.href = `http://${hostLocation}:8080/api/patient/${element}/dicom`;
        });
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
                                selectedPatientIDList={selectedPatientIDList}
                                metaDataUpdated={metaDataUpdated}
                                setMetaDataUpdated={setMetaDataUpdated}
                        />
                        )}
                    </Fragment>
                    <Tooltip title="Download">
                        <IconButton onClick={() => handleDownloadButtonClick(selectedPatientIDList)}>
                            <CloudDownloadIcon />
                        </IconButton>
                    </Tooltip>
                </Stack>
            ) : (
                <Tooltip title="Filter list">
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