/**
 * reducer는 store에 들어갈 state와 state를 바꿀 함수를 정의하는 곳입니다.
 * 기본적으로 순수함수로 코딩하고, 불변성을 지켜야 합니다.
 *
 */
import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import { composeWithDevTools } from 'redux-devtools-extension'
import reducer from './reducer'

const composedEnhancer = composeWithDevTools(applyMiddleware(thunkMiddleware))

// The store now has the ability to accept thunk functions in `dispatch`
const store = createStore(reducer, composedEnhancer)
export default store