// Register custom SVG icons globally using Vite's import.meta.glob
// SVG files in ./svg/ are loaded as raw strings and registered as functional components

const svgModules = import.meta.glob('./svg/*.svg', { eager: true, query: '?raw' })

export default {
  install(app) {
    Object.entries(svgModules).forEach(([path, module]) => {
      // Extract file name without extension as component name
      const name = path.replace(/^\.\/svg\/(.+)\.svg$/, '$1')
      // Register as a simple SVG render component
      app.component(`svg-icon-${name}`, {
        functional: true,
        render(h, { data }) {
          return h('span', {
            ...data,
            innerHTML: module.default,
            class: ['svg-icon', data?.class],
            style: { display: 'inline-flex', ...data?.style }
          })
        }
      })
    })
  }
}
