import * as React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';
import { useState } from 'react';

export default function DicomRow(props) {
    const { isItemSelected, labelId, handleClick, row, keys, isNonReferenced } = props;
    const [dragged, setDragged] = useState(false);

    // 메타 데이터 형식 변경으로 인한 임시 키
    const STUDY_KEY_NAME = "StudyInstanceUID";

    const createTableCell = (rowBody) => {
        const elements = [];
        for (let i = 1; i < keys.length; i++) {
            elements[i - 1] = <TableCell style={{ whiteSpace: 'pre-wrap' }} key={keys[i]}>{rowBody[keys[i]]}</TableCell>;
        }
        return elements;
    };

    const redirectViewer = () => {
        const hostLocation=process.env.REACT_APP_SERVER_HOST
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
                onMouseDown={() => setDragged(false)}
                onMouseMove={() => setDragged(true)}
                onMouseUp={redirectViewer}
            >
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
                    {row.body[keys[0]]}
                </TableCell>
                {createTableCell(row.body)}
            </TableRow>
        </React.Fragment>
    );
}