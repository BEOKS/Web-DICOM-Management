import * as React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import StudyTable from './StudyTable';

export default function DicomRow(props) {
    const [open, setOpen] = React.useState(false);
    const { isItemSelected, labelId, handleClick, row, keys } = props;

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
                onClick={(event) => handleClick(event, row.metadataId)}
                role="checkbox"
                aria-checked={isItemSelected}
                tabIndex={-1}
                selected={isItemSelected}
            >
                <TableCell sx={{ width: '34px' }}>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={(event) => {
                            event.stopPropagation();
                            setOpen(!open);
                        }}
                    >
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        checked={isItemSelected}
                        inputProps={{
                            'aria-labelledby': labelId,
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
            <StudyTable open={open} colSpan={keys.length + 2} patientId={row.body[keys[0]]} />
        </React.Fragment>
    );
}