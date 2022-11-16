/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Objet2_04(props) {
  const { nodes, materials } = useGLTF('/Objet2/Objet2_04.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.Santas_Sleigh_1.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('/Objet2/Objet2_04.glb')
export default Objet2_04
