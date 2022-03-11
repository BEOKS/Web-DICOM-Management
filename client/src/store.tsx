import {combineReducers} from "redux";
import ColumnTypeDecisionDialogReducer
    from "./component/Toolbar/Upload/MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";
import ParticipantInfoReducer from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";

const rootReducer=combineReducers({
    ColumnTypeDecisionDialogReducer,
    ParticipantInfoReducer
})

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;