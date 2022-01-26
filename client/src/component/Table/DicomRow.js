import * as React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';

export default function DicomRow(props) {
    const { isItemSelected, labelId, handleClick, row, keys, isNonReferenced } = props;

    const createTableCell = (rowBody) => {
        const elements = [];
        for (let i = 1; i < keys.length; i++) {
            elements[i - 1] = <TableCell align="right" key={keys[i]}>{rowBody[keys[i]]}</TableCell>;
        }
        return elements;
    };
    return (
        <React.Fragment>
            <TableRow
                hover
                role="checkbox"
                aria-checked={isItemSelected}
                tabIndex={-1}
                selected={isItemSelected}
            >
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        checked={isItemSelected}
                        inputProps={{
                            'aria-labelledby': labelId,
                        }}
                        onClick={(event) => {
                            const id = isNonReferenced ? row.body.patientId : row.metadataId;
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