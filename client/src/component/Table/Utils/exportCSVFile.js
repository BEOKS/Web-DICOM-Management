import {parse} from "json2csv";

export default function exportCSVFile(items, fileTitle) {
    const fields = Object.keys(items[0]);
    const opts = { fields };
    var csv = parse(items,opts);
    console.log("158",csv,items)
    var exportedFilenmae = fileTitle || 'export.csv';

    var blob = new Blob(["\ufeff"+csv], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, exportedFilenmae);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", exportedFilenmae);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}
//exportCSVFile(headers, itemsFormatted, fileTitle);