/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Objet3_09(props) {
  const { nodes, materials } = useGLTF('/Objet3/Objet3_09.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.Ice_Cream_1.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('/Objet3/Objet3_09.glb')
export default Objet3_09
