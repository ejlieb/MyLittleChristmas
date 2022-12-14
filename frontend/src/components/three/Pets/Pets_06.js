/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Pets_06(props) {
  const { nodes, materials } = useGLTF('/Pets/seal.glb')
  return (
    <group {...props} dispose={null}>
      <group rotation={[-Math.PI / 2, 0, 0]} scale={66.55}>
        <mesh geometry={nodes.Seal_LOD0_1.geometry} material={materials['Mat.5']} />
      </group>
    </group>
  )
}

useGLTF.preload('/Pets/seal.glb')
export default Pets_06
