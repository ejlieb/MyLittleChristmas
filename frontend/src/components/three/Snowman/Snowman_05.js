/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Snowman_05(props) {
  const { nodes, materials } = useGLTF('/Snowman/Snowman_05.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.Snowman_4.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('/Snowman/Snowman_05.glb')
export default Snowman_05
