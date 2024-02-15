import daisyui from 'daisyui'

/** @type {import('tailwindcss').Config} */
export default {
  prefix: "tw-",
  content: ["./src/**/*.{html,js,ts,vue}"],
  theme: {
    extend: {},
  },
  plugins: [daisyui],
  daisyui: {
    themes: ["cupcake", "dark", "cmyk"],
    darkTheme: "dark",
    base: true,
    styled: true,
    utils: true,
    logs: true,
    themeRoot: ":root",
  },
}

