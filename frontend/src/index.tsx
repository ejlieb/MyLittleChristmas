import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider, } from 'react-redux';
import { store } from './app/store';
import reportWebVitals from './reportWebVitals';
import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import StyledEngineProvider from '@mui/material/StyledEngineProvider';

import Board from './pages/board'
import Collection from './pages/collection'
import CustomMain from './pages/custommain'
import Friends from './pages/friends'
import Index from './pages/intro'
import Tutorial  from './pages/tutorial'
import UnityBackGround  from './pages/unitybackground'
import persistStore from 'redux-persist/es/persistStore';
import { PersistGate } from 'redux-persist/integration/react';
const container = document.getElementById('root')!;
const root = createRoot(container);

const persistor = persistStore(store)
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <StyledEngineProvider injectFirst>
        <PersistGate persistor={persistor}>
          <BrowserRouter>
          <Routes>
            <Route path="/" element={<Index/>}></Route>
            <Route path="/board" element={<Board/>}></Route>
            <Route path="/collection" element={<Collection/>}></Route>
            <Route path="/custommain/:userid" element={<CustomMain/>}></Route>
            <Route path="/friends/:userid" element={<Friends/>}></Route>
            <Route path="/tutorial" element={<Tutorial/>}></Route>
            <Route path="/unitybackground" element={<UnityBackGround/>}></Route>
          </Routes>
          </BrowserRouter>
        </PersistGate>
      </StyledEngineProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
