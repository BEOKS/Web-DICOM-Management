import * as React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';
import { useState } from 'react';
import { IconButton, Tooltip, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import MLResultTableRow from "./MLResult/MLResultTableRow";

const MAX_CELL_STRING_LENGTH = 100
export default function DicomRow(props) {
    const { isItemSelected, labelId, handleClick, row, rowIndex, keys, isNonReferenced } = props;
    const [dragged, setDragged] = useState(false);
    const [openCollapse, setOpenCollapse] = useState(false)
    const [editMode, setEditMode] = useState(false);

    const handleEdit = () => {
        setEditMode(true);
    };
    const handleSave = (index) => {
        console.log(index);
        setEditMode(false);
    }

    // 메타 데이터 형식 변경으로 인한 임시 키
    const STUDY_KEY_NAME = "StudyInstanceUID";
    const ANONYMIZED_ID = "anonymized_id"
    const IMAGE_NAME = "image_name"

    const createTableCell = (rowBody) => {
        const elements = [];
        for (let i = 1; i < keys.length + 1; i++) {
            if (!editMode) { // Text mode
                if (i === keys.length) { // Edit button
                    elements[i - 1] =
                        <TableCell style={{ whiteSpace: 'pre-wrap' }} key="editButton">
                            <IconButton aria-label="edit" onClick={() => handleEdit()}>
                                <EditIcon />
                            </IconButton>
                        </TableCell>;

                } else { // Other columns (text)
                    if (rowBody[keys[i]].length >= MAX_CELL_STRING_LENGTH) {
                        elements[i - 1] =
                            <Tooltip key={keys[i]}
                                title={
                                    <div style={{ whiteSpace: 'pre-line' }}>{rowBody[keys[i]]}</div>
                                }>
                                <TableCell style={{ whiteSpace: 'pre-wrap' }} key={keys[i]}>
                                    {rowBody[keys[i]].slice(0, MAX_CELL_STRING_LENGTH) + "..."}
                                </TableCell>
                            </Tooltip>;
                    }
                    else {
                        elements[i - 1] =
                            <TableCell style={{ whiteSpace: 'pre-wrap' }} key={keys[i]}>{rowBody[keys[i]]}</TableCell>;
                    }
                }
            } else { // Edit mode
                if (i === keys.length) { // Save button
                    elements[i - 1] =
                        <TableCell style={{ whiteSpace: 'pre-wrap' }} key="saveButton">
                            <IconButton aria-label="edit" onClick={() => handleSave(rowIndex)}>
                                <SaveIcon />
                            </IconButton>
                        </TableCell>;

                } else { // Other columns (TextField)
                    elements[i - 1] =
                        <TableCell style={{ whiteSpace: 'pre-wrap' }} key={keys[i]}>
                            <TextField id={labelId} size="small" variant="outlined" defaultValue={rowBody[keys[i]]} />
                        </TableCell>;
                }
            }
        }
        return elements;
    };

    const redirectViewer = () => {
        const hostLocation = process.env.REACT_APP_SERVER_HOST
        const viewerHost = `http://${hostLocation}:3000`;
        const studyUID = row.body[STUDY_KEY_NAME];

        if (dragged === false) {
            window.open(`${viewerHost}/viewer/${studyUID}`, '_blank').focus();
        }
    };

    return (
        <React.Fragment>
            <TableRow
                hover
                role="checkbox"
                aria-checked={isItemSelected}
                tabIndex={-1}
                selected={isItemSelected}
            // MLResultTable을 보기 위해서 일시적으로 비활성화
            // onMouseDown={() => setDragged(false)}
            // onMouseMove={() => setDragged(true)}
            // onMouseUp={redirectViewer}
            >
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpenCollapse(!openCollapse)}
                    >
                        {openCollapse ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        checked={isItemSelected}
                        inputProps={{
                            'aria-labelledby': labelId,
                        }}
                        onMouseDown={(event) => {
                            const id = isNonReferenced ? row.body.patientId : row.metadataId;
                            event.stopPropagation();
                            handleClick(event, id);
                        }}
                    />
                </TableCell>
                <TableCell
                    component="th"
                    id={labelId}
                    scope="row"
                    padding="none"
                >
                    {!editMode ? row.body[keys[0]] : <TextField size="small" variant="outlined" defaultValue={row.body[keys[0]]} />}
                </TableCell>
                {createTableCell(row.body)}
            </TableRow>
            <MLResultTableRow
                image_name={row.body[IMAGE_NAME]}
                anonymize_id={row.body[ANONYMIZED_ID]}
                open={openCollapse}
                pred={row.body['pred']}
                prob={row.body['prob']}
            />
        </React.Fragment>
    );
}