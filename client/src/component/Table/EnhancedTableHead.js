import * as React from "react";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Checkbox from "@mui/material/Checkbox";
import TableSortLabel from "@mui/material/TableSortLabel";
import Box from "@mui/material/Box";
import {visuallyHidden} from "@mui/utils";
import PropTypes from "prop-types";
import {Tooltip} from "@mui/material";

/**
 * 입력받은 string이 최대 사이즈를 초과할 경우 요약후
 * 나머지 내용을 툴팁으로 보여주는 래퍼 뷰입니다.
 * @param string
 * @constructor
 */
const SummarizeComponent=({str, MAX_SIZE = 10})=>{
    // str=String(str)
    if (str.length<=MAX_SIZE){
        return (<span>{str}</span>)
    }
    else{
        return (
            <Tooltip
                     title={
                         <div style={{ whiteSpace: 'pre-line' }}>{str}</div>
                     }>
                <span>{str.slice(0,MAX_SIZE)+"..."}</span>
            </Tooltip>
        )
    }
}
/**
 * 테이블에서 속성 및 전체 선택 체크박스가 표시되는 헤더 뷰입니다.
 * @param props
 * @return {JSX.Element}
 * @constructor
 */
export default function EnhancedTableHead(props) {
    let headCells = [];
    const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort, rows, keys } =
        props;
    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    const createHeadCell = (id, numeric, disablePadding, label) => {
        if (!isNaN(rows[0].body[id])) {
            numeric = true;
        }

        return {
            id,
            numeric,
            disablePadding,
            label,
        };
    };

    const pushHeadCells = () => {
        for (let i = 0; i < keys.length; i++) {
            const disablePadding = i === 0 ? true : false;
            headCells[i] = createHeadCell(keys[i], false, disablePadding, keys[i]);
        }
    };
    pushHeadCells();

    return (
        <TableHead>
            <TableRow>
                <TableCell></TableCell>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        indeterminate={numSelected > 0 && numSelected < rowCount}
                        checked={rowCount > 0 && numSelected === rowCount}
                        onChange={onSelectAllClick}
                        inputProps={{
                            'aria-label': 'select all desserts',
                        }}
                    />
                </TableCell>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        // align={headCell.numeric ? 'right' : 'left'}
                        align={headCell.id === 'anonymized_id' || headCell.id === 'patientId' ? 'left' : 'center'}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            <SummarizeComponent str={headCell.label}/>
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

EnhancedTableHead.propTypes = {
    numSelected: PropTypes.number.isRequired,
    onRequestSort: PropTypes.func.isRequired,
    onSelectAllClick: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
    rows: PropTypes.array.isRequired,
    keys: PropTypes.array.isRequired,
};