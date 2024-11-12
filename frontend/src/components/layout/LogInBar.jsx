import React, { useEffect } from "react";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import { Form, Formik } from "formik";
import { InputText } from "primereact/inputtext";
import { FloatLabel } from "primereact/floatlabel";
import { Link } from "react-router-dom";
import { Password } from 'primereact/password';
import { GlobalLoadingContext } from "../../contexts/GlobalLoadingContext";

// eslint-disable-next-line react/prop-types
function LogIn({ show, handleClose }) {

    return (
        <>
            <Dialog
                visible={show}
                modal
                onHide={() => handleClose()}
                content={({ hide }) => (
                    <div className="flex flex-col text-center items-center justify-center p-4 gap-4 bg-gradient-to-tr from-slate-700 rounded-xl backdrop-blur-sm">
                        <h1 className="select-none p-3 font-bold">EG</h1>
                        <Formik
                            initialValues={{ email: "", password: "" }}
                            onSubmit={(values, { setSubmitting }) => {
                                setTimeout(() => {
                                    alert(JSON.stringify(values, null, 2));
                                    setSubmitting(false);
                                }, 400);
                            }}
                        >
                            {({
                                values,
                                handleChange,
                                handleBlur,
                                handleSubmit,
                                isSubmitting,
                            }) => (
                                <Form
                                    onSubmit={handleSubmit}
                                    className="flex gap-7 flex-col p-4"
                                >
                                    <FloatLabel>
                                        <InputText
                                            type="email"
                                            name="email"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="p-3"
                                            value={values.email}
                                        />
                                        <label htmlFor="email">Email</label>
                                    </FloatLabel>
                                    <div className="flex flex-col">
                                        <FloatLabel>
                                            <Password
                                                name="password"
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                className="[&>_*_.p-password-input]:p-3"
                                                value={values.password}
                                                toggleMask
                                                feedback={false}
                                            />
                                            <label htmlFor="password">
                                                Password
                                            </label>
                                        </FloatLabel>
                                        <Link to="recoverPassword" className="text-xs self-start p-1">
                                            Forgotten Password?
                                        </Link>
                                    </div>
                                    <div className="flex align-items-center gap-2">
                                        <Button
                                            label="Cancel"
                                            disabled={isSubmitting}
                                            onClick={(e) => hide(e)}
                                            text
                                            className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10"
                                        />
                                        <Button
                                            label="Login"
                                            type="submit"
                                            disabled={isSubmitting}
                                            onClick={(e) => hide(e)}
                                            text
                                            className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10"
                                        />
                                    </div>
                                </Form>
                            )}
                        </Formik>
                    </div>
                )}
            ></Dialog>
        </>
    );
}

export default LogIn;
