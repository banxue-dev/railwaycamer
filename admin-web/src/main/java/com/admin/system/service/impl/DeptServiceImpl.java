package com.admin.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.common.domain.Tree;
import com.admin.common.utils.BuildTree;
import com.admin.railway.dao.StationDao;
import com.admin.railway.domain.StationDO;
import com.admin.system.dao.DeptDao;
import com.admin.system.domain.DeptDO;
import com.admin.system.service.DeptService;



@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao sysDeptMapper;
    @Autowired
    private StationDao stationMapper;

	@Override
	public DeptDO get(Long deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<DeptDO> list(Map<String, Object> map){
		return sysDeptMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int save(DeptDO sysDept){
		return sysDeptMapper.save(sysDept);
	}

	@Override
	public int update(DeptDO sysDept){
		return sysDeptMapper.update(sysDept);
	}

	@Override
	public int remove(Long deptId){
		return sysDeptMapper.remove(deptId);
	}

	@Override
	public int batchRemove(Long[] deptIds){
		return sysDeptMapper.batchRemove(deptIds);
	}

	@Override
	public Tree<DeptDO> getTree(List<Long> ids) {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("sort", "id");
		map.put("order", "asc");
		map.put("type", "1");
		List<StationDO> StationDOs = stationMapper.list(map);
//		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		List<StationDO> Stations =new ArrayList<StationDO>();
		if(ids==null || ids.size()<1) {
			Stations=StationDOs;
		}else {
			Stations=getIDS(StationDOs,Stations,ids);
		}
		for (StationDO sysDept : Stations) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(sysDept.getId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			tree.setHavaIds(ids.toString());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	public List<StationDO>  getIDS(List<StationDO> StationDOs,List<StationDO> Stations,List<Long> ids ) {
		for(StationDO sd:StationDOs) {
			if(ids.indexOf(sd.getId())!=-1) {
				if(Stations.indexOf(sd)==-1) {
					//只加一次
					Stations.add(sd);
				}
				if(sd.getParentId()!=0) {
					List<Long> lst=new ArrayList<Long>();
					lst.add(sd.getParentId());
					getIDS(StationDOs,Stations,lst);
				}
			}
		}
		return Stations;
	}
	@Override
	public boolean checkDeptHasUser(Long deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		return result==0?true:false;
	}

}
