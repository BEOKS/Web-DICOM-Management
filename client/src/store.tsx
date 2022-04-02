import {combineReducers} from "redux";
import ColumnTypeDecisionDialogReducer
    from "./component/Toolbar/Upload/MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";
import ParticipantInfoReducer from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";
import SamplingReducer from "./component/Toolbar/Sampling/SamplingReducer";
import MLReducer from "./component/Toolbar/MachineLearning/MLReducer";

const rootReducer=combineReducers({
    ColumnTypeDecisionDialogReducer,
    ParticipantInfoReducer,
    SamplingReducer,
    MLReducer
})

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;