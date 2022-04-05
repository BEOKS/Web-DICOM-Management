import {combineReducers} from "redux";
import ColumnTypeDecisionDialogReducer
    from "./component/Toolbar/Upload/MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";
import ParticipantInfoReducer from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";
import SamplingReducer from "./component/Toolbar/Sampling/SamplingReducer";
import MLReducer from "./component/Toolbar/MachineLearning/MLReducer";
import FormatChooseReducer from "./component/Toolbar/Upload/FormatChooseDialog/FormatChooseDialogReducer";
import FileUploadDialogReducer from "./component/Toolbar/Upload/FileUploadDialog/FileUploadDialogReducer";
const rootReducer=combineReducers({
    ColumnTypeDecisionDialogReducer,
    ParticipantInfoReducer,
    SamplingReducer,
    MLReducer,
    FormatChooseReducer,
    FileUploadDialogReducer
})

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;