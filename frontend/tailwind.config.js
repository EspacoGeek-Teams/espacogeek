/** @type {import('tailwindcss').Config} */
import plugin from 'tailwindcss/plugin';

export const content = ["./src/**/*.{js,jsx,ts,tsx}"];
export const theme = {
    extend: {},
};
export const plugins = [
    plugin(function ({ addVariant, e, postcss }) {
        addVariant("firefox", ({ container, separator }) => {
            const isFirefoxRule = postcss.atRule({
                name: "-moz-document",
                params: "url-prefix()",
            });
            isFirefoxRule.append(container.nodes);
            container.append(isFirefoxRule);
            isFirefoxRule.walkRules((rule) => {
                rule.selector = `.${e(
                    `firefox${separator}${rule.selector.slice(1)}`
                )}`;
            });
        });
    }),
];
