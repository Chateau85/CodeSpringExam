import { copyFile, mkdir } from 'node:fs/promises';
import { dirname, resolve } from 'node:path';
const root = resolve(import.meta.dirname, '..');
const assets = [
  ['node_modules/jquery/dist/jquery.js', 'vendor/jquery/jquery.js'],
  ['node_modules/jquery/dist/jquery.min.js', 'vendor/jquery/jquery.min.js'],
  ['node_modules/jquery/dist/jquery.min.map', 'vendor/jquery/jquery.min.map'],
  ['node_modules/raphael/raphael.js', 'vendor/raphael/raphael.js'],
  ['node_modules/raphael/raphael.min.js', 'vendor/raphael/raphael.min.js']
];
for (const moduleName of ['CodeSpringEx02', 'CodeSpringEx03', 'CodeSpringEx04', 'CodeSpringEx05']) {
  const resourceRoot = resolve(root, moduleName, 'src/main/webapp/resources');
  for (const [source, relativeTarget] of assets) {
    const target = resolve(resourceRoot, relativeTarget);
    await mkdir(dirname(target), { recursive: true });
    await copyFile(resolve(import.meta.dirname, source), target);
  }
}
console.log('Synchronized jQuery 3.7.1 and Raphael 2.3.0 into CodeSpringEx02/03.');

