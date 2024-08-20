import React from "react";
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from "react-router-dom";
import RecoverPassword from "./../containers/recoverPassword/RecoverPassword";
import Home from "./../containers/home/Home";

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route path="/" element={<Home />} />
            <Route path="recoverPassword" element={<RecoverPassword />} />
        </>
    )
);

export default router;
