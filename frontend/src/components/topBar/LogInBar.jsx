import React from "react";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import { Form, Formik } from "formik";
import { InputText } from "primereact/inputtext";
import { FloatLabel } from "primereact/floatlabel";

// eslint-disable-next-line react/prop-types
function LogIn({ show, handleClose }) {
    const headerElement = (
        <div className="inline-flex align-items-center justify-content-center gap-2">
            <span className="font-bold white-space-nowrap">Login</span>
        </div>
    );

    const footerContent = (
        <div>
            <Button
                label="Ok"
                icon="pi pi-check"
                onClick={() => handleClose()}
                autoFocus
            />
        </div>
    );

    return (
        <>
            <Dialog
                visible={show}
                modal
                onHide={() => handleClose()}
                content={({ hide }) => (
                    <div
                        className="flex flex-col text-center items-center justify-center p-4 gap-4 bg-gradient-to-r from-indigo-500"
                        style={{
                            borderRadius: "12px",
                            // backgroundImage: "radial-gradient(circle at left top, var(--primary-800), var(--primary-900))",
                        }}
                    >
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
                                errors,
                                touched,
                                handleChange,
                                handleBlur,
                                handleSubmit,
                                isSubmitting,
                            }) => (
                                <Form onSubmit={handleSubmit} className="flex gap-7 flex-col">
                                    <FloatLabel>
                                        <InputText
                                            type="email"
                                            name="email"
                                            required={false}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.email}
                                        />
                                        {errors.email &&
                                            touched.email &&
                                            errors.email}
                                        <label htmlFor="email">Email</label>
                                    </FloatLabel>
                                    <FloatLabel>
                                        <InputText
                                            type="password"
                                            name="password"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            className="bg-white-alpha-20 border-none p-3 text-primary-50"
                                            value={values.password}
                                        />
                                        {errors.password &&
                                            touched.password &&
                                            errors.password}
                                        <label htmlFor="password">
                                            Password
                                        </label>
                                    </FloatLabel>
                                    <div className="flex align-items-center gap-2">
                                        <Button
                                            label="Login"
                                            type="submit"
                                            disabled={isSubmitting}
                                            onClick={(e) => hide(e)}
                                            text
                                            className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10"
                                        />
                                        <Button
                                            label="Cancel"
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
