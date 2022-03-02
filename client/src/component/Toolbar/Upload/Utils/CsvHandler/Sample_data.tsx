import {CsvSlices} from "./CsvLabelDivider";
import {DICOM_ID, LABEL} from "../../MetadataColumnTypeDecision/ColumnTypeDesicionDialog";

export const csvJsonExample = [{
    'ER': "1",
    'HER2': "1",
    'PR': "1",
    'StudyInstanceUID': "1.2.410.2000010.82.242.1018932208290001",
    'age': "53",
    'anonymized_id': "00000003",
    'class non-pCR: 0 pCR: 1': "0",
    'compressionForce': "173.5019",
    'left: 0 right: 1': "1",
    'manufacturer': "HOLOGIC, Inc.",
    'manufacturerModelName': "Lorad Selenia",
    'modality': "MG",
    'non-IDC: 0 IDC: 1': "1",
    'series_id': '1',
    'instance_id': '1',
    'page number': 1
},
    {
        'ER': "0",
        'HER2': "0",
        'PR': "0",
        'StudyInstanceUID': "123",
        'age': "56",
        'anonymized_id': "1106526",
        'class non-pCR: 0 pCR: 1': "1",
        'compressionForce': "115.6019",
        'left: 0 right: 1': "0",
        'manufacturer': "HOLOGIC, Inc.",
        'manufacturerModelName': "Lorad Selenia",
        'modality': "MG",
        'non-IDC: 0 IDC: 1': "1",
        'series_id': '2',
        'instance_id': '2',
        'page number': 2
    }
]
export const schemaExample = {
    'ER': LABEL.PATIENT,
    'HER2': LABEL.PATIENT,
    'PR': LABEL.PATIENT,
    'StudyInstanceUID': DICOM_ID.STUDY,
    'age': LABEL.PATIENT,
    'anonymized_id': DICOM_ID.PATIENT,
    'class non-pCR: 0 pCR: 1': LABEL.PATIENT,
    'compressionForce': LABEL.PATIENT,
    'left: 0 right: 1': LABEL.PATIENT,
    'manufacturer': LABEL.PATIENT,
    'manufacturerModelName': LABEL.PATIENT,
    'modality': LABEL.SERIES,
    'non-IDC: 0 IDC: 1': LABEL.PATIENT,
    'series_id': DICOM_ID.SERIES,
    'instance_id': DICOM_ID.INSTANCE,
    'page number': LABEL.INSTANCE
}
export const csvSlicesExample = new CsvSlices()