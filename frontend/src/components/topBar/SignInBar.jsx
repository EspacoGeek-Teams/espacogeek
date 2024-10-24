import React, { useContext } from "react";
import { useMutation } from "@apollo/client";
import signInMutation from "../apollo/schemas/mutations/signInUser";
import { Dialog } from "primereact/dialog";
import { useFormik } from "formik";
import { InputText } from "primereact/inputtext";
import { FloatLabel } from "primereact/floatlabel";
import { Button } from "primereact/button";
import { Password } from 'primereact/password';
import { ErrorContext } from "../../contexts/ErrorContext";
import { SuccessContext } from "../../contexts/SuccessContext";
import * as Yup from 'yup';

// eslint-disable-next-line react/prop-types
function SignIn({ show, handleClose }) {
    const cssInvalid = "[&>_*_.p-inputtext]:border-red-500 [&>_*_.p-inputtext]:border-[1px]";
    const [newUser, { loading }] = useMutation(signInMutation);
    const { setErrorMessage } = useContext(ErrorContext);
    const { setSuccessMessage } = useContext(SuccessContext);

    async function handleSubmit(values) {
        try {
            const { data } = await newUser({
                variables: {
                    username: values.username,
                    email: values.email,
                    password: values.password,
                },
            });
            setSuccessMessage(data.createUser);
            handleClose();
        } catch (error) {
            const errorMsg = error.graphQLErrors?.[0]?.message || "An error occurred";
            setErrorMessage(errorMsg);
        }
    }

    const validation = Yup.object().shape({
        username: Yup.string()
            .min(3, "Username must be at least 3 characters")
            .max(20, "Username must be at most 20 characters"),
        email: Yup.string()
            .email("Invalid email"),
        password: Yup.string()
            .min(8, "Password must be at least 8 characters")
            .max(70, "Password must be at most 70 characters"),
        confirmPassword: Yup.string()
            .oneOf([Yup.ref('password'), null], 'Passwords must match'),
    });

    const formik = useFormik({
        initialValues: {
            email: "",
            username: "",
            password: "",
            confirmPassword: "",
        },
        onSubmit: (values) => handleSubmit(values),
        validationSchema: validation,
    });

    return (
        <>
            <Dialog
                visible={show}
                modal
                closable={!loading}
                onHide={handleClose}
                content={() => (
                    <div className="flex flex-col text-center items-center justify-center p-4 gap-4 bg-gradient-to-tr from-slate-700 rounded-xl">
                        <h1 className="select-none p-3 font-bold">EG</h1>
                        <form className="flex gap-7 flex-col p-4" onSubmit={formik.handleSubmit}>
                            <div className={"flex flex-col " + (formik.errors.email ? cssInvalid : "")}>
                                <FloatLabel>
                                    <InputText
                                        type="email"
                                        name="email"
                                        invalid={formik.errors.email}
                                        required={true}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        aria-describedby="email-help"
                                        className="p-3"
                                        value={formik.values.email} />
                                    <label htmlFor="email">Email</label>
                                </FloatLabel>
                                <small
                                    id="email-help"
                                    className="text-red-300 select-none">
                                    {formik.errors.email}
                                </small>
                            </div>
                            <div className={"flex flex-col " + (formik.errors.username ? cssInvalid : "")}>
                                <FloatLabel>
                                    <InputText
                                        type="text"
                                        name="username"
                                        required={true}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        aria-describedby="username-help"
                                        className="p-3"
                                        value={formik.values.username} />
                                    <label htmlFor="username">
                                        Username
                                    </label>
                                </FloatLabel>
                                <small
                                    id="username-help"
                                    className="text-red-300 select-none">
                                    {formik.errors.username}
                                </small>
                            </div>
                            <div className={"flex flex-col " + (formik.errors.password ? cssInvalid : "")}>
                                <FloatLabel>
                                    <Password
                                        name="password"
                                        required={true}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        invalid={formik.errors.password}
                                        className="[&>_*_.p-password-input]:p-3"
                                        value={formik.values.password}
                                        toggleMask />
                                    <label htmlFor="password">
                                        Password
                                    </label>
                                </FloatLabel>
                                <small
                                    id="confirmPassword-help"
                                    className="text-red-300 select-none">
                                    {formik.errors.password}
                                </small>
                            </div>
                            <div className={"flex flex-col " + (formik.errors.confirmPassword ? cssInvalid : "")}>
                                <FloatLabel>
                                    <Password
                                        name="confirmPassword"
                                        required={true}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        aria-describedby="confirmPassword-help"
                                        className="[&>_*_.p-password-input]:p-3"
                                        value={formik.values.confirmPassword}
                                        toggleMask />
                                    <label htmlFor="confirmPassword">
                                        Confirm Password
                                    </label>
                                </FloatLabel>
                                <small
                                    id="confirmPassword-help"
                                    className="text-red-300 select-none">
                                    {formik.errors.confirmPassword}
                                </small>
                            </div>
                            <div className="flex align-items-center gap-2">
                                <Button
                                    label="Cancel"
                                    disabled={loading || formik.isSubmitting}
                                    type="button"
                                    onClick={handleClose}
                                    text
                                    className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10" />
                                <Button
                                    label="Sing In"
                                    type="submit"
                                    loading={loading || formik.isSubmitting}
                                    text
                                    className="p-3 w-full text-primary-50 border-1 border-white-alpha-30 hover:bg-white-alpha-10" />
                            </div>
                        </form>
                    </div>
                )}
            />
        </>
    );
}

export default SignIn;
