import {combineReducers} from "redux";
import ColumnTypeDecisionDialogReducer
    from "./component/Toolbar/Upload/MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";
import ParticipantInfoReducer from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";
import SamplingReducer from "./component/Toolbar/Sampling/SamplingReducer";

const rootReducer=combineReducers({
    ColumnTypeDecisionDialogReducer,
    ParticipantInfoReducer,
    SamplingReducer
})

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;