import { readFile } from 'node:fs/promises';
import { resolve } from 'node:path';
const root = resolve(import.meta.dirname, '..');
for (const moduleName of ['CodeSpringEx02', 'CodeSpringEx03', 'CodeSpringEx04', 'CodeSpringEx05']) {
  const resourceRoot = resolve(root, moduleName, 'src/main/webapp/resources/vendor');
  const jquery = await readFile(resolve(resourceRoot, 'jquery/jquery.js'), 'utf8');
  const raphael = await readFile(resolve(resourceRoot, 'raphael/raphael.js'), 'utf8');
  if (!jquery.includes('jQuery JavaScript Library v3.7.1')) throw new Error(`${moduleName}: unexpected jQuery`);
  if (!raphael.includes('2.3.0')) throw new Error(`${moduleName}: unexpected Raphael`);
}
console.log('Frontend asset versions verified.');

